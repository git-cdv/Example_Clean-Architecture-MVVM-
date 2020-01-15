package com.example.manuel.baseproject.data.datasource.api

import com.example.manuel.baseproject.data.datasource.api.retrofit.BeersApiService
import java.lang.Exception

import com.example.manuel.baseproject.core.datatype.Result
import com.example.manuel.baseproject.data.datasource.api.exceptions.handleNetworkExceptions
import com.example.manuel.baseproject.data.datasource.api.model.BeerApi

const val MAX_RESULTS_PER_PAGE: Int = 80

class BeersNetworkDataSource(private val beersApiService: BeersApiService) {

    suspend fun getAllBeers(page: String): Result<List<BeerApi>> {
        return try {
            val beers = beersApiService.getAllBeers(page, MAX_RESULTS_PER_PAGE.toString())
            Result.success(beers)
        } catch (ex: Exception) {
            Result.error(handleNetworkExceptions(ex))
        }
    }
}
