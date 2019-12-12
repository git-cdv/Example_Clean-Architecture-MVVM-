package com.example.manuel.baseproject.cache.model

import com.example.manuel.baseproject.network.data.datasource.cache.Cacheable

data class BeerCacheModel(
        override var id: Int,
        val name: String,
        val tagline: String,
        val image: String,
        val abv: Double,
        var isFavorite: Boolean = false
) : Cacheable