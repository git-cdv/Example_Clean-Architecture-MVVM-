package com.example.manuel.baseproject.data.datasource.api.mapper

import com.example.manuel.baseproject.core.BaseMapper
import com.example.manuel.baseproject.data.datasource.api.model.api.BeerApi
import com.example.manuel.baseproject.data.datasource.api.model.response.BeerResponse
import com.example.manuel.baseproject.data.datasource.api.model.api.BeersApi

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