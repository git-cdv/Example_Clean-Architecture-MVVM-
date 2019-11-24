package com.example.manuel.baseproject.home.beers.domain.usecase

import com.example.manuel.baseproject.home.beers.domain.BeersRepository
import com.example.manuel.baseproject.home.beers.domain.model.BeerEntity

class RemoveBeerUseCase(private val beersRepository: BeersRepository) {

    suspend fun execute(id: Int): Boolean {
        return beersRepository.removeBeer(id)
    }
}