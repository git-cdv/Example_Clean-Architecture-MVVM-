package com.example.manuel.baseproject.home.beers.domain.usecase

import android.util.Log
import com.example.manuel.baseproject.home.beers.domain.BeersRepository
import com.example.manuel.baseproject.home.beers.domain.model.BeerEntity

class SaveBeerUseCase(private val beersRepository: BeersRepository) {

    suspend fun execute(beerEntity: BeerEntity): Boolean {
        Log.i("test", "save beer id = ${beerEntity.id}")
        return beersRepository.saveBeer(beerEntity)
    }
}