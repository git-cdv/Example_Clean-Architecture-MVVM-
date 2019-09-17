package com.example.manuel.baseproject.home.repository.mapper

import com.example.manuel.baseproject.home.commons.BaseMapper
import com.example.manuel.baseproject.home.datasource.model.api.BeersApi
import com.example.manuel.baseproject.home.domain.model.BeerEntity
import com.example.manuel.baseproject.home.domain.model.BeersEntity

class BeersRepositoryMapper {

    object ApiToEntityMapper: BaseMapper<BeersApi, BeersEntity> {
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
}
