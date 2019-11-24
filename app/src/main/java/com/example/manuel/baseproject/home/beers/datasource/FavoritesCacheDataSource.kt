package com.example.manuel.baseproject.home.beers.datasource

import android.util.Log
import com.example.manuel.baseproject.home.beers.datasource.model.cache.BeerCacheModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File


class FavoritesCacheDataSource(private val gson: Gson, private val favoritesBeersFile: File) {

    fun saveBeer(beerCacheModel: BeerCacheModel): Boolean {
        if (favoritesBeersFile.isFile) {
            val json: String = favoritesBeersFile.readText(Charsets.UTF_8)
            val favoriteBeers = deserializeJsonToObject(json)
            val mutableFavoriteBeers = favoriteBeers.toMutableList()
            mutableFavoriteBeers.add(beerCacheModel)
            favoritesBeersFile.writeText(serializeObjectToJSON(mutableFavoriteBeers))
        } else {
            favoritesBeersFile.writeText(serializeObjectToJSON(listOf(beerCacheModel)))
        }

        return isBeerSaved(beerCacheModel.id)
    }

    private fun isBeerSaved(beerId: Int): Boolean {
        var isBeerSaved = false

        getBeers()?.apply {
            isBeerSaved = any { savedBeer -> savedBeer.id == beerId }
        }

        if (isBeerSaved) Log.i("test", "Beer saved id = $beerId")

        return isBeerSaved
    }

    fun removeBeer(beerCacheModel: BeerCacheModel): Boolean {
        if (favoritesBeersFile.isFile) {
            val json: String = favoritesBeersFile.readText(Charsets.UTF_8)
            val mutableFavoritesBeers = deserializeJsonToObject(json).toMutableList()

            mutableFavoritesBeers.let {
                it.remove(beerCacheModel)
                favoritesBeersFile.writeText(serializeObjectToJSON(mutableFavoritesBeers.toList()))
            }
        }


        return isBeerRemoved(beerCacheModel.id)
    }

    private fun isBeerRemoved(beerId: Int): Boolean {
        var isBeerRemoved = false

        getBeers()?.apply {
            isBeerRemoved = any { savedBeer -> savedBeer.id == beerId }
        }

        if (isBeerRemoved) Log.i("test", "Beer removed id = $beerId")

        return isBeerRemoved
    }

    private fun serializeObjectToJSON(beersCacheModel: List<BeerCacheModel>): String {
        return gson.toJson(beersCacheModel)
    }

    fun getBeers(): List<BeerCacheModel>? {
        val json: String = favoritesBeersFile.readText(Charsets.UTF_8)

        return deserializeJsonToObject(json)
    }

    // TODO Use generics
    private fun deserializeJsonToObject(beerJson: String): List<BeerCacheModel> {
        val listType = object : TypeToken<ArrayList<BeerCacheModel?>?>() {}.type

        return gson.fromJson(beerJson, listType) ?: listOf()
    }
}