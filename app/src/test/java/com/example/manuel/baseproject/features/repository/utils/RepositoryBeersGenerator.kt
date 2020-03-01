package com.example.manuel.baseproject.features.repository.utils

import com.example.manuel.baseproject.features.beers.domain.model.BeerEntity
import com.example.manuel.baseproject.features.beers.domain.model.BeersEntity

object RepositoryBeersGenerator {

    fun getBeersEntity(): BeersEntity {
        return BeersEntity(
                listOf(
                        BeerEntity(
                                id = 1,
                                name = "",
                                tagline = "",
                                image = "",
                                abv = -1.0,
                                foodPairing = listOf("pork, beef")
                        ),
                        BeerEntity(
                                id = 2,
                                name = "Beer two",
                                tagline = "Tagline two",
                                image = "Image two",
                                abv = 2.0,
                                foodPairing = emptyList()
                        ),
                        BeerEntity(
                                id = 3,
                                name = "Beer three",
                                tagline = "Tagline three",
                                image = "Image three",
                                abv = 2.0,
                                foodPairing = listOf("chicken")
                        ),
                        BeerEntity(
                                id = 4,
                                name = "Beer four",
                                tagline = "Tagline four",
                                image = "Image four",
                                abv = -1.0,
                                foodPairing = listOf("pork, beef, chicken")
                        ),
                        BeerEntity(
                                id = 5,
                                name = "",
                                tagline = "",
                                image = "",
                                abv = -1.0,
                                foodPairing = listOf("pork")
                        )
                )
        )
    }
}