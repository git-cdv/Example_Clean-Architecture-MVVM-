package com.example.manuel.baseproject.network.data.datasource.cache

import com.example.manuel.baseproject.network.data.datasource.cache.model.BeerCacheModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

class FavoritesCacheDataSource(private val gson: Gson, private val favoritesBeersFile: File) {

    fun saveBeer(beerCacheModel: BeerCacheModel): Boolean {
        val listToSave = if (favoritesBeersFile.isFile) {
            val mutableFavoriteBeers = getBeers().toMutableList()
            mutableFavoriteBeers.add(beerCacheModel)
            mutableFavoriteBeers
        } else {
            val firstBeerWhenFileIsEmpty = listOf(beerCacheModel)
            firstBeerWhenFileIsEmpty
        }

        favoritesBeersFile.writeText(serializeObjectToJSON(listToSave))

        return isBeerSaved(beerCacheModel.id)
    }

    private fun isBeerSaved(beerId: Int): Boolean {
        return getBeers().any { savedBeer -> savedBeer.id == beerId }
    }

    fun removeBeer(id: Int): Boolean {
        if (favoritesBeersFile.isFile) {
            val mutableBeers = getBeers().toMutableList()
            val existBeer = mutableBeers.any { it.id == id }
            if (existBeer) {
                val beerToRemove = mutableBeers.filter { it.id == id }[0]
                mutableBeers.remove(beerToRemove)
                favoritesBeersFile.writeText(serializeObjectToJSON(mutableBeers.toList()))
            }
        }

        return isBeerRemoved(id)
    }

    private fun isBeerRemoved(beerId: Int): Boolean {
        return getBeers().any { savedBeer -> savedBeer.id == beerId }.not()
    }

    private fun serializeObjectToJSON(beersCacheModel: List<BeerCacheModel>): String {
        return gson.toJson(beersCacheModel)
    }

    fun getBeers(): List<BeerCacheModel> {
        val json = if (favoritesBeersFile.isFile) {
            favoritesBeersFile.readText(Charsets.UTF_8)
        } else {
            ""
        }

        return deserializeJsonToObject(json)
    }

    private fun deserializeJsonToObject(beerJson: String): List<BeerCacheModel> {
        val listType = object : TypeToken<ArrayList<BeerCacheModel?>?>() {}.type
        return gson.fromJson(beerJson, listType) ?: mutableListOf()
    }
}
