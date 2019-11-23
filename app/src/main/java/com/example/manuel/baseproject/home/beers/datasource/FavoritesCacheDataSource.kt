package com.example.manuel.baseproject.home.beers.datasource

import android.util.Log
import com.example.manuel.baseproject.home.beers.datasource.model.cache.BeerCacheModel
import com.google.gson.Gson

class FavoritesCacheDataSource(private val gson: Gson) {

    init {
        Log.i("test", "CacheDataSource")
    }

    suspend fun getBeers(): BeerCacheModel? {
        // Read from local JSON
        return null
    }

    suspend fun saveBeer(beerCacheModel: BeerCacheModel): Boolean {
        // Save in local json
        serializeObjectToJSON(beerCacheModel)
        return true
    }

    suspend fun removeBeer() {
        // remove in json local
    }

    private fun serializeObjectToJSON(beerCacheModel: BeerCacheModel) {
        val jsonString = gson.toJson(beerCacheModel)

        val objectFromJson = gson.fromJson(jsonString, BeerCacheModel::class.java)
        Log.i("test", "jsonString = $jsonString")
        Log.i("test", "objectFromJson = $objectFromJson")
    }

    // TODO Use generics
    private fun deserializeJsonToObject(beerJson: String) {
        val objectFromJson = gson.fromJson(beerJson, BeerCacheModel::class.java)
    }
}