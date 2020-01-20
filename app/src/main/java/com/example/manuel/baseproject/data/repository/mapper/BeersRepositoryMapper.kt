package com.example.manuel.baseproject.data.repository.mapper

import com.example.manuel.baseproject.core.BaseMapper
import com.example.manuel.baseproject.data.datasource.api.model.BeerApi
import com.example.manuel.baseproject.data.datasource.local.model.BeerLocalModel
import com.example.manuel.baseproject.features.beers.domain.model.BeerEntity
import com.example.manuel.baseproject.features.beers.domain.model.BeersEntity

object ApiToEntityMapper : BaseMapper<List<BeerApi>, BeersEntity> {
    override fun map(type: List<BeerApi>?): BeersEntity {
        return BeersEntity(
                type?.map {
                    BeerEntity(
                            id = it.id ?: -1,
                            name = it.name ?: "",
                            tagline = it.tagline ?: "",
                            image = it.image ?: "",
                            abv = it.abv ?: -1.0,
                            isFavorite = false,
                            foodPairing = it.foodPairing ?: emptyList()
                    )
                } ?: listOf()
        )
    }
}

object EntityToCacheMapper : BaseMapper<BeerEntity, BeerLocalModel> {
    override fun map(type: BeerEntity?): BeerLocalModel {
        return BeerLocalModel(
                id = type!!.id,
                name = type.name,
                tagline = type.tagline,
                image = type.image,
                abv = type.abv,
                isFavorite = true,
                foodPairing = type.foodPairing
        )
    }
}

object CacheToEntityMapper : BaseMapper<List<BeerLocalModel>, BeersEntity> {
    override fun map(type: List<BeerLocalModel>?): BeersEntity {
        return BeersEntity(
                beers = type?.map {
                    BeerEntity(
                            id = it.id,
                            name = it.name,
                            tagline = it.tagline,
                            image = it.image,
                            abv = it.abv,
                            isFavorite = it.isFavorite,
                            foodPairing = it.foodPairing
                    )
                } ?: listOf()
        )
    }
}

object ApiToLocalModelMapper : BaseMapper<List<BeerApi>, List<BeerLocalModel>> {
    override fun map(type: List<BeerApi>?): List<BeerLocalModel> {
        return type?.map {
            BeerLocalModel(
                    id = it.id ?: -1,
                    name = it.name ?: "",
                    tagline = it.tagline ?: "",
                    image = it.image ?: "",
                    abv = it.abv ?: 0.0,
                    isFavorite = false,
                    foodPairing = it.foodPairing ?: emptyList()
            )
        } ?: emptyList()
    }
}
