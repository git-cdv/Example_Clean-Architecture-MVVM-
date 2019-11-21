package com.example.manuel.baseproject.home.datasource

import com.example.manuel.baseproject.home.commons.datasource.handleNetworkExceptions
import com.example.manuel.baseproject.home.datasource.retrofit.BeersApiService
import java.lang.Exception

import com.example.manuel.baseproject.home.commons.datatype.Result
import com.example.manuel.baseproject.home.datasource.mapper.ResponseToApiMapper
import com.example.manuel.baseproject.home.datasource.model.api.BeersApi

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
