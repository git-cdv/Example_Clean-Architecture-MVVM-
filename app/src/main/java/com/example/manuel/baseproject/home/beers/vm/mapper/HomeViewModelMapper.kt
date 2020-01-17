package com.example.manuel.baseproject.home.beers.vm.mapper

import com.example.manuel.baseproject.core.BaseMapper
import com.example.manuel.baseproject.home.beers.vm.model.AbvColorType
import com.example.manuel.baseproject.home.beers.domain.model.AbvRangeType
import com.example.manuel.baseproject.home.beers.domain.model.BeerEntity
import com.example.manuel.baseproject.home.beers.vm.model.BeerUI

object BeersEntityToUIMapper : BaseMapper<List<BeerEntity>, List<BeerUI>> {
    override fun map(type: List<BeerEntity>?): List<BeerUI> {
        return type?.map {
            BeerUI(
                    id = it.id,
                    name = it.name,
                    tagline = it.tagline,
                    image = it.image,
                    abv = it.abv,
                    abvColorType = mapAbvType(it.getAbvRange(it.abv)),
                    isFavorite = it.isFavorite,
                    foodPairing = it.foodPairing.map { foodPairing ->
                        "- $foodPairing"
                    }
            )
        } ?: listOf()
    }

    private fun mapAbvType(abvRangeType: AbvRangeType): AbvColorType {
        return when (abvRangeType) {
            AbvRangeType.LOW -> AbvColorType.GREEN
            AbvRangeType.NORMAL -> AbvColorType.ORANGE
            AbvRangeType.HIGH -> AbvColorType.RED
        }
    }
}

object BeerUIToEntityMapper : BaseMapper<BeerUI, BeerEntity> {
    override fun map(type: BeerUI?): BeerEntity {
        return BeerEntity(
                id = type!!.id,
                name = type.name,
                tagline = type.tagline,
                image = type.image,
                abv = type.abv,
                isFavorite = type.isFavorite,
                foodPairing = type.foodPairing.map {
                    it.removePrefix("-")
                }
        )
    }
}
