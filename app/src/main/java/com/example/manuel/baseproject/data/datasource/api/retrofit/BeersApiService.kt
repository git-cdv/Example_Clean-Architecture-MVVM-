package com.example.manuel.baseproject.data.datasource.api.retrofit

import com.example.manuel.baseproject.data.datasource.api.model.BeerApi
import retrofit2.http.GET
import retrofit2.http.Query

interface BeersApiService {

    @GET("beers?")
    suspend fun getAllBeers(
            @Query("page") page: String,
            @Query("per_page") perPage: String
    ): List<BeerApi>?
}