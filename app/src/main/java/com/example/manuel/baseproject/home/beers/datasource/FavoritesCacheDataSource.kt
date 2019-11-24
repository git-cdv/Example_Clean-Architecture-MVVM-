package com.example.manuel.baseproject.home.beers.datasource

import android.util.Log
import com.example.manuel.baseproject.home.beers.datasource.model.cache.BeerCacheModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File


class FavoritesCacheDataSource(private val gson: Gson, private val favoritesBeersFile: File) {

    fun getBeers(): List<BeerCacheModel>? {
        val json: String = favoritesBeersFile.readText(Charsets.UTF_8)

        return deserializeJsonToObject(json)
    }

    fun saveBeer(beerCacheModel: BeerCacheModel): Boolean {
        var isBeerSaved = false

        if (favoritesBeersFile.isFile) {
            val json: String = favoritesBeersFile.readText(Charsets.UTF_8)
            val favoriteBeers = deserializeJsonToObject(json)
            val mutableFavoriteBeers = favoriteBeers.toMutableList()
            mutableFavoriteBeers.add(beerCacheModel)
            favoritesBeersFile.writeText(serializeObjectToJSON(mutableFavoriteBeers))
        } else {
            favoritesBeersFile.writeText(serializeObjectToJSON(listOf(beerCacheModel)))
        }

        getBeers()?.apply {
            isBeerSaved = any { savedBeer -> savedBeer.id == beerCacheModel.id }
        }

        if (isBeerSaved) Log.i("test", "Beer saved id = ${beerCacheModel.id}")

        return isBeerSaved
    }

    fun removeBeer(beerCacheModel: BeerCacheModel): Boolean {
        // Para borrar, recupero toda la lista a objetos
        var isBeerRemoved = false

        if (favoritesBeersFile.isFile) {
            val json: String = favoritesBeersFile.readText(Charsets.UTF_8)
            val mutableFavoritesBeers = deserializeJsonToObject(json).toMutableList()

            mutableFavoritesBeers.let {
                it.remove(beerCacheModel)
                favoritesBeersFile.writeText(serializeObjectToJSON(mutableFavoritesBeers.toList()))


                // Para saber si he borrado, tengo que leer y comprobar si existe en la lista
                getBeers()?.apply {
                    isBeerRemoved = any { savedBeer -> savedBeer.id == beerCacheModel.id }
                }
            }
        }

        if (isBeerRemoved) Log.i("test", "Se borra beer id = ${beerCacheModel.id}")

        return isBeerRemoved
    }

    private fun serializeObjectToJSON(beersCacheModel: List<BeerCacheModel>): String {
        return gson.toJson(beersCacheModel)
    }

    // TODO Use generics
    private fun deserializeJsonToObject(beerJson: String): List<BeerCacheModel> {
        val listType = object : TypeToken<ArrayList<BeerCacheModel?>?>() {}.type

        return gson.fromJson(beerJson, listType) ?: listOf()
    }
}