package com.example.manuel.baseproject.network.data.datasource.api

import com.example.manuel.baseproject.network.handleNetworkExceptions
import com.example.manuel.baseproject.network.data.datasource.api.retrofit.BeersApiService
import java.lang.Exception

import com.example.manuel.baseproject.core.datatype.Result
import com.example.manuel.baseproject.network.data.datasource.api.mapper.ResponseToApiMapper
import com.example.manuel.baseproject.network.data.datasource.api.model.api.BeersApi

const val MAX_RESULTS_PER_PAGE: Int = 80

class BeersNetworkDataSource(private val beersApiService: BeersApiService) {

    suspend fun getAllBeers(page: String): Result<BeersApi> {
        return try {
            val beers = beersApiService.getAllBeers(page, MAX_RESULTS_PER_PAGE.toString())
            Result.success(ResponseToApiMapper.map(beers))
        } catch (ex: Exception) {
            Result.error(handleNetworkExceptions(ex))
        }
    }
}
