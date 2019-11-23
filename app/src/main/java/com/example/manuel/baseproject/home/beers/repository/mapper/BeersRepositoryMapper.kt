package com.example.manuel.baseproject.home.beers.repository.mapper

import com.example.manuel.baseproject.commons.BaseMapper
import com.example.manuel.baseproject.home.beers.datasource.model.api.BeersApi
import com.example.manuel.baseproject.home.beers.datasource.model.cache.BeerCacheModel
import com.example.manuel.baseproject.home.beers.domain.model.BeerEntity
import com.example.manuel.baseproject.home.beers.domain.model.BeersEntity

object ApiToEntityMapper : BaseMapper<BeersApi, BeersEntity> {
    override fun map(type: BeersApi?): BeersEntity {
        return BeersEntity(
                type?.beers?.map {
                    BeerEntity(
                            id = it.id ?: -1,
                            name = it.name ?: "",
                            tagline = it.tagline ?: "",
                            image = it.image ?: "",
                            abv = it.abv ?: -1.0
                    )
                } ?: listOf()
        )
    }
}

object EntityToCacheMapper : BaseMapper<BeerEntity, BeerCacheModel> {
    override fun map(type: BeerEntity?): BeerCacheModel {
        return BeerCacheModel(
                id = type!!.id,
                name = type.name,
                tagline = type.tagline,
                image = type.image,
                abv = type.abv,
                isFavorite = type.isFavorite
        )
    }
}
