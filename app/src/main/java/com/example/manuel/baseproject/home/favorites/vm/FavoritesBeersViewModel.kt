package com.example.manuel.baseproject.home.favorites.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.manuel.baseproject.home.beers.domain.usecase.RemoveBeerUseCase
import com.example.manuel.baseproject.home.beers.domain.usecase.SaveBeerUseCase
import com.example.manuel.baseproject.home.beers.vm.mapper.BeerAdapterModelToEntityMapper
import com.example.manuel.baseproject.home.beers.vm.mapper.BeersEntityToUIMapper
import com.example.manuel.baseproject.home.beers.vm.model.BeerUI
import com.example.manuel.baseproject.home.favorites.domain.GetFavoritesBeersUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoritesBeersViewModel(
        private val getFavoritesBeersUseCase: GetFavoritesBeersUseCase,
        private val removeBeerUseCase: RemoveBeerUseCase,
        private val saveBeerUseCase: SaveBeerUseCase
) : ViewModel() {

    private var beerUIRemoved: BeerUI? = null
    private var positionBeerUIRemoved = -1
    private val mutableBeersUI: MutableList<BeerUI> = mutableListOf()
    private var isUndoButtonPressed = false

    private val beersMutableLiveData: MutableLiveData<List<BeerUI>> = MutableLiveData()
    val beersLiveData: LiveData<List<BeerUI>>
        get() = beersMutableLiveData

    private val isErrorMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val isErrorLiveData: LiveData<Boolean>
        get() = isErrorMutableLiveData

    private val isLoadingMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val isLoadingLiveData: LiveData<Boolean>
        get() = isLoadingMutableLiveData

    init {
        handleBeersLoad()
    }

    private fun handleBeersLoad() {
        isLoadingLiveData(true)
        viewModelScope.launch(Dispatchers.IO) {
            val beersUI = BeersEntityToUIMapper.map(getFavoritesBeersUseCase.execute().beers)
            beersMutableLiveData.postValue(beersUI)
            isLoadingLiveData(false)
            mutableBeersUI.addAll(beersUI)
        }
    }

    private fun isLoadingLiveData(isLoading: Boolean) {
        this.isLoadingMutableLiveData.postValue(isLoading)
    }

    fun handleRemoveButton(beerUI: BeerUI) {
        this.beerUIRemoved = beerUI
        removeBeerUITemporalCache()
        beersMutableLiveData.postValue(mutableBeersUI)
        removeBeerUseCase.execute(beerUI.id)
    }

    private fun removeBeerUITemporalCache() {
        val beerToRemoved = mutableBeersUI.filter { it.id == beerUIRemoved?.id }[0]
        this.positionBeerUIRemoved = mutableBeersUI.indexOf(beerToRemoved)
        mutableBeersUI.remove(beerToRemoved)
    }

    fun handleUndoButton() {
        this.isUndoButtonPressed = true
        saveBeerUseCase.execute(BeerAdapterModelToEntityMapper.map(beerUIRemoved))
        restorePreviousState()
        resetBeerRemovedData()
    }

    private fun restorePreviousState() {
        beerUIRemoved?.let { beerUIRemoved ->
            mutableBeersUI.apply {
                add(positionBeerUIRemoved, beerUIRemoved.apply { isFavorite = true })
                sortedBy { it.abv }
            }
            beersMutableLiveData.postValue(mutableBeersUI)
        }
    }

    private fun resetBeerRemovedData() {
        positionBeerUIRemoved = -1
        beerUIRemoved = null
    }

    fun handleOnSnackBarHidden() {
        //if (!isUndoButtonPressed) saveBeerUseCase.execute(BeerAdapterModelToEntityMapper.map(beerUIRemoved))
        isUndoButtonPressed = false
    }
}