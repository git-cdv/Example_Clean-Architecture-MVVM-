package com.example.manuel.baseproject.home.repository.utils

import com.example.manuel.baseproject.data.datasource.api.model.BeerApi
import com.example.manuel.baseproject.data.datasource.api.model.api.BeersApi
import com.example.manuel.baseproject.home.beers.domain.model.BeerEntity
import com.example.manuel.baseproject.home.beers.domain.model.BeersEntity

object RepositoryBeersGenerator {

    fun getBeersApi(): BeersApi {
        return BeersApi(
                listOf(
                        BeerApi(
                                id = 1,
                                name = "",
                                tagline = "",
                                image = "",
                                abv = -1.0
                        ),
                        BeerApi(
                                id = 2,
                                name = "Beer two",
                                tagline = "Tagline two",
                                image = "Image two",
                                abv = 2.0
                        ),
                        BeerApi(
                                id = 3,
                                name = "Beer three",
                                tagline = "Tagline three",
                                image = "Image three",
                                abv = 2.0
                        ),
                        BeerApi(
                                id = 4,
                                name = "Beer four",
                                tagline = "Tagline four",
                                image = "Image four",
                                abv = -1.0
                        ),
                        BeerApi(
                                id = 5,
                                name = null,
                                tagline = null,
                                image = null,
                                abv = null
                        )
                )
        )
    }

    fun getBeersEntity(): BeersEntity {
        return BeersEntity(
                listOf(
                        BeerEntity(
                                id = 1,
                                name = "",
                                tagline = "",
                                image = "",
                                abv = -1.0
                        ),
                        BeerEntity(
                                id = 2,
                                name = "Beer two",
                                tagline = "Tagline two",
                                image = "Image two",
                                abv = 2.0
                        ),
                        BeerEntity(
                                id = 3,
                                name = "Beer three",
                                tagline = "Tagline three",
                                image = "Image three",
                                abv = 2.0
                        ),
                        BeerEntity(
                                id = 4,
                                name = "Beer four",
                                tagline = "Tagline four",
                                image = "Image four",
                                abv = -1.0
                        ),
                        BeerEntity(
                                id = 5,
                                name = "",
                                tagline = "",
                                image = "",
                                abv = -1.0
                        )
                )
        )
    }
}