package com.example.manuel.baseproject.home.datasource.retrofit

import com.example.manuel.baseproject.home.datasource.model.response.BeerResponse
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface BeersApiService {

    @GET("beers?")
    fun getAllBeersAsync(@Query("page") page: String,
                         @Query("per_page") perPage: String): Deferred<List<BeerResponse>>?

    @GET("beers?")
    fun getAllBeersAsync2(@Query("page") page: String,
                         @Query("per_page") perPage: String): Call<List<BeerResponse>>?
}