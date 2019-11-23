package com.example.manuel.baseproject.home.beers.datasource.model.cache

data class BeerCacheModel(
        val id: Int,
        val name: String,
        val tagline: String,
        val image: String,
        val abv: Double,
        val abvColor: Int,
        var isFavorite: Boolean = false
)