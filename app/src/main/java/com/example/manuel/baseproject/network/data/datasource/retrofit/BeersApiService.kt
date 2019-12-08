package com.example.manuel.baseproject.network.data.datasource.retrofit

import com.example.manuel.baseproject.network.data.datasource.model.response.BeerResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface BeersApiService {

    @GET("beers?")
    suspend fun getAllBeers(
            @Query("page") page: String,
            @Query("per_page") perPage: String
    ): List<BeerResponse>?
}