package com.example.manuel.baseproject.home.beers.datasource.mapper

import com.example.manuel.baseproject.commons.BaseMapper
import com.example.manuel.baseproject.home.beers.datasource.model.api.BeerApi
import com.example.manuel.baseproject.home.beers.datasource.model.response.BeerResponse
import com.example.manuel.baseproject.home.beers.datasource.model.api.BeersApi

object ResponseToApiMapper : BaseMapper<List<BeerResponse>, BeersApi> {
    override fun map(type: List<BeerResponse>?): BeersApi {
        return BeersApi(type?.map {
            BeerApi(
                    id = it.id ?: -1,
                    name = it.name ?: "",
                    tagline = it.tagline ?: "",
                    image = it.image ?: "",
                    abv = it.abv ?: -1.0
            )
        } ?: listOf())
    }
}
