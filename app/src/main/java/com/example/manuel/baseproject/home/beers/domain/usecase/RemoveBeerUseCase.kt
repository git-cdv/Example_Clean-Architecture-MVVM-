package com.example.manuel.baseproject.home.beers.domain.usecase

import com.example.manuel.baseproject.home.beers.domain.BeersRepository

class RemoveBeerUseCase(private val beersRepository: BeersRepository) {

    fun execute(id: Int): Boolean {
        return beersRepository.removeBeer(id)
    }
}