package com.example.manuel.baseproject.home.beers.domain

import com.example.manuel.baseproject.commons.datatype.Result
import com.example.manuel.baseproject.home.beers.domain.model.BeerEntity
import com.example.manuel.baseproject.home.beers.domain.model.BeersEntity

interface BeersRepository {

    suspend fun getAllBeers(): Result<BeersEntity>?
    suspend fun saveBeer(beerEntity: BeerEntity): Boolean
    suspend fun removeBeer(beerEntity: BeerEntity): Boolean
}