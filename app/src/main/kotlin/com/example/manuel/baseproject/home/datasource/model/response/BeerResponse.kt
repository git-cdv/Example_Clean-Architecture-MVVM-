package com.example.manuel.baseproject.home.datasource.model.response

import com.google.gson.annotations.SerializedName

data class BeerResponse(
        @SerializedName("id") val id: Int? = null,
        @SerializedName("name") val name: String? = null,
        @SerializedName("tagline") val tagline: String? = null,
        @SerializedName("image_url") val image: String? = null,
        @SerializedName("abv") val abv: Double? = null
)