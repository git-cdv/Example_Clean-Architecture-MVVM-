package com.example.manuel.baseproject.home.beers.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.manuel.baseproject.commons.datatype.Result
import com.example.manuel.baseproject.commons.datatype.ResultType
import com.example.manuel.baseproject.home.beers.domain.model.BeersEntity
import com.example.manuel.baseproject.home.beers.domain.usecase.GetBeersUseCase
import com.example.manuel.baseproject.home.beers.domain.usecase.RemoveBeerUseCase
import com.example.manuel.baseproject.home.beers.domain.usecase.SaveBeerUseCase
import com.example.manuel.baseproject.home.beers.vm.mapper.BeerAdapterModelToEntityMapper
import com.example.manuel.baseproject.home.beers.vm.mapper.BeersEntityToUIMapper
import com.example.manuel.baseproject.home.beers.vm.model.BeerUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel(
        private val getMealsByBeersUseCase: GetBeersUseCase,
        private val saveBeerUseCase: SaveBeerUseCase,
        private val removeBeerUseCase: RemoveBeerUseCase
) : ViewModel() {

    private val beersLiveData: MutableLiveData<List<BeerUI>> = MutableLiveData()
    private val areEmptyBeersLiveData: MutableLiveData<Boolean> = MutableLiveData()
    private val isErrorLiveData: MutableLiveData<Boolean> = MutableLiveData()
    private val isLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData()

    val beers: LiveData<List<BeerUI>>
        get() = beersLiveData

    val areEmptyBeers: LiveData<Boolean>
        get() = areEmptyBeersLiveData

    val isError: LiveData<Boolean>
        get() = isErrorLiveData

    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    init {
        handleBeersLoad()
    }

    fun handleBeersLoad() {
        isLoadingLiveData(true)
        viewModelScope.launch(Dispatchers.IO) {
            updateAppropriateLiveData(getMealsByBeersUseCase.execute())
        }
    }

    private fun updateAppropriateLiveData(result: Result<BeersEntity>) {
        if (isResultSuccess(result.resultType)) {
            onResultSuccess(result.data)
        } else {
            onResultError()
        }
    }

    private fun isResultSuccess(resultType: ResultType): Boolean {
        return resultType == ResultType.SUCCESS
    }

    private fun onResultSuccess(beersEntity: BeersEntity?) {
        val beers = BeersEntityToUIMapper.map(beersEntity?.beers)

        if (beers.isEmpty()) {
            areEmptyBeersLiveData.postValue(true)
        } else {
            beersLiveData.postValue(beers)
        }

        isLoadingLiveData(false)
    }

    /**
     *  The delay is to avoid the screen flash between the transition from AlertDialog to ProgressBar
     * */
    private fun onResultError() {
        viewModelScope.launch {
            delay(300)
            isLoadingLiveData(false)
        }.invokeOnCompletion {
            isErrorLiveData.postValue(true)
        }
    }

    private fun isLoadingLiveData(isLoading: Boolean) {
        this.isLoadingLiveData.postValue(isLoading)
    }

    fun handleFavoriteButton(beerUI: BeerUI) {
        viewModelScope.launch(Dispatchers.IO) {
            BeerAdapterModelToEntityMapper.map(beerUI).let {
                if (it.isFavorite) saveBeerUseCase.execute(it).apply {
                    // Si ocurre un error, modificar el beerUI con isFavorite al estado anterior
                    // Y actualizar el livedata
                }
                else removeBeerUseCase.execute(it).apply {

                }
            }
        }
    }
}
