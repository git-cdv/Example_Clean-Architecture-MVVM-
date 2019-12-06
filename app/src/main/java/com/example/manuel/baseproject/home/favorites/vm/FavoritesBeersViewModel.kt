package com.example.manuel.baseproject.home.favorites.vm

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
import kotlinx.coroutines.withContext

class FavoritesBeersViewModel(
        private val getFavoritesBeersUseCase: GetFavoritesBeersUseCase,
        private val removeBeerUseCase: RemoveBeerUseCase,
        private val saveBeerUseCase: SaveBeerUseCase
) : ViewModel() {

    private var beerUIRemoved: BeerUI? = null
    private var positionBeerUIRemoved = -1
    private val mutableBeersUI: MutableList<BeerUI> = mutableListOf()

    private val beersMutableLiveData = MutableLiveData<List<BeerUI>>()
    val beersLiveData: LiveData<List<BeerUI>>
        get() = beersMutableLiveData

    private val isErrorMutableLiveData = MutableLiveData<Boolean>()
    val isErrorLiveData: LiveData<Boolean>
        get() = isErrorMutableLiveData

    private val isLoadingMutableLiveData = MutableLiveData<Boolean>()
    val isLoadingLiveData: LiveData<Boolean>
        get() = isLoadingMutableLiveData

    private var counterRemovedBeers = 0
    private val isSomeBeerRemovedMutableLiveData = MutableLiveData<Boolean>()
    val isSomeBeerRemovedLiveData: LiveData<Boolean>
        get() = isSomeBeerRemovedMutableLiveData

    init {
        handleBeersLoad()
    }

    private fun handleBeersLoad() {
        isLoadingLiveData(true)
        viewModelScope.launch(Dispatchers.IO) {
            val beersUI = BeersEntityToUIMapper.map(getFavoritesBeersUseCase.execute().beers)
            withContext(Dispatchers.Main) {
                beersMutableLiveData.value = beersUI
                isLoadingLiveData(false)
                mutableBeersUI.addAll(beersUI)
            }
        }
    }

    private fun isLoadingLiveData(isLoading: Boolean) {
        this.isLoadingMutableLiveData.value = isLoading
    }

    fun handleRemoveButton(beerUI: BeerUI) {
        this.beerUIRemoved = beerUI
        this.counterRemovedBeers++
        removeBeerUITemporalCache()
        beersMutableLiveData.value = mutableBeersUI.toList()
        removeBeerUseCase.execute(beerUI.id)
    }

    private fun removeBeerUITemporalCache() {
        val beerToRemoved = mutableBeersUI.filter { it.id == beerUIRemoved?.id }[0]
        this.positionBeerUIRemoved = mutableBeersUI.indexOf(beerToRemoved)
        mutableBeersUI.remove(beerToRemoved)
    }

    fun handleUndoButton() {
        this.counterRemovedBeers--
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
            beersMutableLiveData.value = mutableBeersUI.toList()
        }
    }

    private fun resetBeerRemovedData() {
        positionBeerUIRemoved = -1
        beerUIRemoved = null
    }

    fun handleOnBackPressed() {
        isSomeBeerRemovedMutableLiveData.value = counterRemovedBeers > 0
    }
}