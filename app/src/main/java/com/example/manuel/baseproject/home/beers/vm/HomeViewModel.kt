package com.example.manuel.baseproject.home.beers.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.manuel.baseproject.core.datatype.Result
import com.example.manuel.baseproject.core.datatype.ResultType
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
import kotlinx.coroutines.withContext

class HomeViewModel(
        private val getMealsByBeersUseCase: GetBeersUseCase,
        private val saveBeerUseCase: SaveBeerUseCase,
        private val removeBeerUseCase: RemoveBeerUseCase
) : ViewModel() {

    private val beersMutableLiveData: MutableLiveData<List<BeerUI>> = MutableLiveData()
    val beersLiveData: LiveData<List<BeerUI>>
        get() = beersMutableLiveData

    private val areEmptyBeersMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val areEmptyBeersLiveData: LiveData<Boolean>
        get() = areEmptyBeersMutableLiveData

    private val isErrorMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val isErrorLiveData: LiveData<Boolean>
        get() = isErrorMutableLiveData

    private val isLoadingMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val isLoadingLiveData: LiveData<Boolean>
        get() = isLoadingMutableLiveData

    init {
        handleBeersLoad()
    }

    fun handleBeersLoad() {
        isLoadingLiveData(true)
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                updateAppropriateLiveData(getMealsByBeersUseCase.execute())
            }
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
            areEmptyBeersMutableLiveData.value = true
        } else {
            beersMutableLiveData.value = beers
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
            isErrorMutableLiveData.value = true
        }
    }

    private fun isLoadingLiveData(isLoading: Boolean) {
        this.isLoadingMutableLiveData.value = isLoading
    }

    fun handleFavoriteButton(beerUI: BeerUI) {
        viewModelScope.launch(Dispatchers.IO) {
            BeerAdapterModelToEntityMapper.map(beerUI).let {
                if (it.isFavorite) saveBeerUseCase.execute(it).let { isBeerSaved ->
                    if (!isBeerSaved) {
                        // TODO Handle the error, modify the beerUI with isfavorite to previous state and update the livedata
                    }
                }
                else removeBeerUseCase.execute(it.id).let { isBeerRemoved ->
                    if (!isBeerRemoved) {
                        // TODO Handle the error, modify the beerUI with isfavorite to previous state and update the livedata
                    }
                }
            }
        }
    }
}
