package com.example.manuel.baseproject.home.beers.datasource.model.cache

data class BeersCacheModel(
        val data: MutableList<BeerCacheModel>?
)

data class BeerCacheModel(
        val id: Int,
        val name: String,
        val tagline: String,
        val image: String,
        val abv: Double,
        var isFavorite: Boolean = false
)