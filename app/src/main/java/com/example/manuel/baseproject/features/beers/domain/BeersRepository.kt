package com.example.manuel.baseproject.features.beers.domain

import com.example.manuel.baseproject.core.datatype.Result
import com.example.manuel.baseproject.features.beers.domain.model.BeerEntity
import com.example.manuel.baseproject.features.beers.domain.model.BeersEntity

interface BeersRepository {

    suspend fun getAllBeers(): Result<BeersEntity>?
    fun saveBeer(beerEntity: BeerEntity): Boolean
    fun removeBeer(id: Int): Boolean
    fun getFavoriteBeers(): BeersEntity
}