package com.example.manuel.baseproject.home.beers.domain.usecase

import com.example.manuel.baseproject.home.beers.domain.BeersRepository
import com.example.manuel.baseproject.home.beers.domain.model.BeerEntity

class SaveBeerUseCase(private val beersRepository: BeersRepository) {

    fun execute(beerEntity: BeerEntity): Boolean {
        return beersRepository.saveBeer(beerEntity)
    }
}