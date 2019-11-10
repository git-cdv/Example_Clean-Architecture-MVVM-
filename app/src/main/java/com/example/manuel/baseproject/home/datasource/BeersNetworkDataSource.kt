package com.example.manuel.baseproject.home.datasource

import com.example.manuel.baseproject.home.commons.datasource.handleNetworkException
import com.example.manuel.baseproject.home.datasource.retrofit.BeersApiService
import kotlinx.coroutines.*
import java.lang.Exception

import com.example.manuel.baseproject.home.commons.datatype.Result
import com.example.manuel.baseproject.home.commons.exceptions.CancelledFetchDataException
import com.example.manuel.baseproject.home.datasource.mapper.BeersNetworkMapper
import com.example.manuel.baseproject.home.datasource.model.api.BeersApi
import com.example.manuel.baseproject.home.datasource.model.response.BeerResponse

const val MAX_RESULTS_PER_PAGE: Int = 80

class BeersNetworkDataSource(private val beersApiService: BeersApiService) {

    suspend fun getAllBeers(page: String): Result<BeersApi> {
        var result: Result<BeersApi> = Result.success(BeersApi(listOf()))

        withContext(Dispatchers.IO) {
            try {
                val request = beersApiService.getAllBeers(page, MAX_RESULTS_PER_PAGE.toString())
                val response: List<BeerResponse>? = request?.execute()?.body()

                request?.let {
                    if (it.isExecuted) result = Result.success(BeersNetworkMapper.ResponseToApiMapper.map(response))
                    else if (it.isCanceled) result = Result.error(CancelledFetchDataException())
                }
            } catch (ex: Exception) {
                result = Result.error(ex.handleNetworkException())

            }
        }

        return result
    }
}
