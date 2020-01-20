package com.example.manuel.baseproject.features.favorites.domain

import com.example.manuel.baseproject.features.beers.domain.BeersRepository
import com.example.manuel.baseproject.features.beers.domain.model.BeersEntity

class GetFavoritesBeersUseCase(private val repository: BeersRepository) {

    fun execute(): BeersEntity {
        val beersEntity = repository.getFavoriteBeers()
        val sortedBeers = beersEntity.beers.sortedBy { it.abv }
        return BeersEntity(sortedBeers)
    }
}