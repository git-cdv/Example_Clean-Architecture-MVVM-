package com.example.manuel.baseproject.home.beers.domain.usecase

import android.util.Log
import com.example.manuel.baseproject.home.beers.domain.BeersRepository
import com.example.manuel.baseproject.home.beers.domain.model.BeerEntity

class RemoveBeerUseCase(private val beersRepository: BeersRepository) {

    suspend fun execute(beerEntity: BeerEntity): Boolean {
        Log.i("test", "remove beer id = ${beerEntity.id}")
        return beersRepository.removeBeer(beerEntity)
    }
}