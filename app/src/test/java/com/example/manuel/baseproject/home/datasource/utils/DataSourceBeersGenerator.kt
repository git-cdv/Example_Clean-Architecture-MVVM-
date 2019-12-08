package com.example.manuel.baseproject.home.datasource.utils

import com.example.manuel.baseproject.network.data.datasource.api.model.api.BeerApi
import com.example.manuel.baseproject.network.data.datasource.api.model.api.BeersApi
import com.example.manuel.baseproject.network.data.datasource.api.model.response.BeerResponse

object DataSourceBeersGenerator {

    fun getBeersResponse(): List<BeerResponse> {
        return listOf(
                BeerResponse(
                        id = 1,
                        name = "BeerNameOne",
                        tagline = "BeerTaglineOne",
                        image = "urlImageOne",
                        abv = 87.0
                ),
                BeerResponse(
                        id = 2,
                        name = "BeerNameTwo",
                        tagline = "BeerTaglineTwo",
                        image = "urlImageTwo",
                        abv = 5.0
                ),
                BeerResponse(
                        id = 3,
                        name = "BeerNameThree",
                        tagline = "BeerTaglineThree",
                        image = "urlImageThree",
                        abv = 2.0
                ),
                BeerResponse(
                        id = 4,
                        name = "BeerNameFour",
                        tagline = "BeerTaglineFour",
                        image = "urlImageFour",
                        abv = 63.0
                ),
                BeerResponse(
                        id = null,
                        name = null,
                        tagline = null,
                        image = null,
                        abv = null
                )
        )
    }

    fun getBeersApi(): BeersApi {
        return BeersApi(
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