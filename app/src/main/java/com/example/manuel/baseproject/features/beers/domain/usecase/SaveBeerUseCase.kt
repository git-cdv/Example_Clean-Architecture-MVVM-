package com.example.manuel.baseproject.features.beers.domain.usecase

import com.example.manuel.baseproject.features.beers.domain.BeersRepository
import com.example.manuel.baseproject.features.beers.domain.model.BeerEntity

class SaveBeerUseCase(private val beersRepository: BeersRepository) {

    fun execute(beerEntity: BeerEntity): Boolean {
        return beersRepository.saveBeer(beerEntity)
    }
}