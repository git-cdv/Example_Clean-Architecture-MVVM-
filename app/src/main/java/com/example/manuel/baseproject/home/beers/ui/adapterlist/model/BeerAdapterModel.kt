package com.example.manuel.baseproject.home.beers.ui.adapterlist.model

data class BeerAdapterModel(
        val id: Int,
        val name: String,
        val tagline: String,
        val image: String,
        val abv: Double,
        val abvColor: Int,
        var isFavorite: Boolean = false,
        val foodPairing: List<String>
)