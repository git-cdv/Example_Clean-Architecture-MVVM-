package com.example.manuel.baseproject.home.beers.datasource

import android.util.Log
import com.example.manuel.baseproject.home.beers.datasource.model.cache.BeerCacheModel
import com.google.gson.Gson

class FavoritesCacheDataSource(gson: Gson) {

    init {
        Log.i("test", "CacheDataSource")
    }

    suspend fun getBeers(): BeerCacheModel? {
        // Read from local JSON
        return null
    }

    suspend fun saveBeers(beerCacheModel: BeerCacheModel) {
        // Save in local json
    }
}