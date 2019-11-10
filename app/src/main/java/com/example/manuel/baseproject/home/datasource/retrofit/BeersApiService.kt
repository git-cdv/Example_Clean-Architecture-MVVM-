package com.example.manuel.baseproject.home.datasource.retrofit

import com.example.manuel.baseproject.home.datasource.model.response.BeerResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface BeersApiService {

    @GET("beers?")
    fun getAllBeers(@Query("page") page: String,
                    @Query("per_page") perPage: String): Call<List<BeerResponse>>?
}