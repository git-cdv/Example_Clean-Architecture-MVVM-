package com.example.manuel.baseproject.features.vm

import com.example.manuel.baseproject.features.beers.vm.model.AbvColorType
import com.example.manuel.baseproject.features.beers.vm.model.BeerUI

fun getBeersUIFake(): List<BeerUI> = listOf(
        BeerUI(
                1,
                "BeerNameOne",
                "BeerTaglineOne",
                "urlImageOne",
                87.0,
                foodPairing = listOf("pork, beef"),
                abvColorType = AbvColorType.RED
        ),
        BeerUI(
                2,
                "BeerNameTwo",
                "BeerTaglineTwo",
                "urlImageTwo",
                5.0,
                foodPairing = emptyList(),
                abvColorType = AbvColorType.GREEN
        ),
        BeerUI(
                3,
                "BeerNameThree",
                "BeerTaglineThree",
                "urlImageThree",
                2.0,
                foodPairing = listOf("chicken"),
                abvColorType = AbvColorType.GREEN
        ),
        BeerUI(
                4,
                "BeerNameFour",
                "BeerTaglineFour",
                "urlImageFour",
                63.0,
                foodPairing = listOf("pork, beef, chicken"),
                abvColorType = AbvColorType.RED
        ),
        BeerUI(
                5,
                "BeerNameFive",
                "BeerTaglineFive",
                "urlImageFive",
                18.0,
                foodPairing = listOf("pork"),
                abvColorType = AbvColorType.ORANGE
        )
)