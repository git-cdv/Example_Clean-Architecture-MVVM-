package com.example.manuel.baseproject.data.datasource.api.model

import com.google.gson.annotations.SerializedName

data class BeerApi(
        @SerializedName("id") val id: Int? = null,
        @SerializedName("name") val name: String? = null,
        @SerializedName("tagline") val tagline: String? = null,
        @SerializedName("image_url") val image: String? = null,
        @SerializedName("abv") val abv: Double? = null,
        @SerializedName("food_pairing") val foodPairing: List<String>? = null
)