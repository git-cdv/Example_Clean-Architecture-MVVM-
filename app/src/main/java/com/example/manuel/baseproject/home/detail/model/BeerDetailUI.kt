package com.example.manuel.baseproject.home.detail.model

import java.io.Serializable

data class BeerDetailUI(
        val image: String,
        val foodPairing: List<String>
) : Serializable