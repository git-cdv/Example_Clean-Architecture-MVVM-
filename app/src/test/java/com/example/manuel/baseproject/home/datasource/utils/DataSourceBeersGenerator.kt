package com.example.manuel.baseproject.home.datasource.utils

import com.example.manuel.baseproject.data.datasource.api.model.BeerApi
import com.example.manuel.baseproject.data.datasource.api.model.api.BeersApi

object DataSourceBeersGenerator {

    fun getBeersResponse(): List<BeerApi> {
        return listOf(
                BeerApi(
                        id = 1,
                        name = "BeerNameOne",
                        tagline = "BeerTaglineOne",
                        image = "urlImageOne",
                        abv = 87.0
                ),
                BeerApi(
                        id = 2,
                        name = "BeerNameTwo",
                        tagline = "BeerTaglineTwo",
                        image = "urlImageTwo",
                        abv = 5.0
                ),
                BeerApi(
                        id = 3,
                        name = "BeerNameThree",
                        tagline = "BeerTaglineThree",
                        image = "urlImageThree",
                        abv = 2.0
                ),
                BeerApi(
                        id = 4,
                        name = "BeerNameFour",
                        tagline = "BeerTaglineFour",
                        image = "urlImageFour",
                        abv = 63.0
                ),
                BeerApi(
                        id = null,
                        name = null,
                        tagline = null,
                        image = null,
                        abv = null
                )
        )
    }

    fun getBeersApi(): BeerApi {
        return BeerApi(
                listOf(
                        BeerApi(
                                id = 1,
                                name = "BeerNameOne",
                                tagline = "BeerTaglineOne",
                                image = "urlImageOne",
                                abv = 87.0
                        ),
                        BeerApi(
                                id = 2,
                                name = "BeerNameTwo",
                                tagline = "BeerTaglineTwo",
                                image = "urlImageTwo",
                                abv = 5.0
                        ),
                        BeerApi(
                                id = 3,
                                name = "BeerNameThree",
                                tagline = "BeerTaglineThree",
                                image = "urlImageThree",
                                abv = 2.0
                        ),
                        BeerApi(
                                id = 4,
                                name = "BeerNameFour",
                                tagline = "BeerTaglineFour",
                                image = "urlImageFour",
                                abv = 63.0
                        ),
                        BeerApi(
                                id = -1,
                                name = "",
                                tagline = "",
                                image = "",
                                abv = -1.0
                        )
                )
        )
    }
}