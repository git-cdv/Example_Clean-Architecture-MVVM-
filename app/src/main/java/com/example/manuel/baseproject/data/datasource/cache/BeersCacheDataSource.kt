package com.example.manuel.baseproject.data.datasource.cache

import com.example.manuel.baseproject.data.datasource.local.model.BeerLocalModel

class BeersCacheDataSource {

    private val beersMutableList: MutableList<BeerLocalModel> = mutableListOf()
    var beers: List<BeerLocalModel>
        get() = beersMutableList
        set(value) {
            beersMutableList.clear()
            beersMutableList.addAll(value)
        }
}