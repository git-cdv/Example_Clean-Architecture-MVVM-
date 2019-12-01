package com.example.manuel.baseproject.home.favorites.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.manuel.baseproject.home.beers.vm.mapper.BeersEntityToUIMapper
import com.example.manuel.baseproject.home.beers.vm.model.BeerUI
import com.example.manuel.baseproject.home.favorites.domain.GetFavoritesBeersUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoritesBeersViewModel(
        private val getFavoritesBeersUseCase: GetFavoritesBeersUseCase
) : ViewModel() {

    private var beerIdToRemove: Int = -1

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
        }
    }

    private fun handleRemoveButton(beerId: Int) {
        // Actualizo el livedata al instante borrando, necesito la beerId por si pulsan deshacer
        // Si hay un error se restaura
        this.beerIdToRemove = beerId
    }

    private fun handleUndoButton() {

    }

    private fun isLoadingLiveData(isLoading: Boolean) {
        this.isLoadingMutableLiveData.postValue(isLoading)
    }
}