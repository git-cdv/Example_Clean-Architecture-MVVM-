package com.example.manuel.baseproject.home.beers.domain.model

class BeerEntity(
        val id: Int,
        val name: String,
        val tagline: String,
        val image: String,
        val abv: Double,
        var isFavorite: Boolean = false,
        val foodPairing: List<String>
) {
    fun getAbvRange(abv: Double): AbvRangeType {
        return when {
            abv < 5 -> AbvRangeType.LOW
            abv >= 5 && abv < 8 -> AbvRangeType.NORMAL
            else -> AbvRangeType.HIGH
        }
    }
}