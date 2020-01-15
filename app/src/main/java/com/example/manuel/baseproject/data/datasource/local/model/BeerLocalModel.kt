package com.example.manuel.baseproject.data.datasource.local.model

data class BeerLocalModel(
        var id: Int,
        val name: String,
        val tagline: String,
        val image: String,
        val abv: Double,
        var isFavorite: Boolean = false,
        val foodPairing: List<String>
)