package com.example.manuel.baseproject.home.favorites.ui.adapterlist.model

data class FavoriteBeerAdapterModel(
        val id: Int,
        val name: String,
        val tagline: String,
        val image: String,
        val abv: Double,
        val abvColor: Int,
        val isFavorite: Boolean = true,
        val foodPairing: List<String>
)