package com.example.manuel.baseproject.cache.model

data class BeerCacheModel(
        val id: Int,
        val name: String,
        val tagline: String,
        val image: String,
        val abv: Double,
        var isFavorite: Boolean = false
)