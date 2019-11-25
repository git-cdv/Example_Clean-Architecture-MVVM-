package com.example.manuel.baseproject.home.beers.domain.usecase

import com.example.manuel.baseproject.commons.datatype.Result
import com.example.manuel.baseproject.commons.datatype.ResultType
import com.example.manuel.baseproject.home.beers.domain.BeersRepository
import com.example.manuel.baseproject.home.beers.domain.model.BeersEntity

class GetBeersUseCase(private val beersRepository: BeersRepository) {

    suspend fun execute(): Result<BeersEntity> {
        var beers: Result<BeersEntity> = Result.success(BeersEntity(listOf()))

        beersRepository.getAllBeers()?.let { beersEntity ->
            val resultType = beersEntity.resultType

            if (resultType == ResultType.SUCCESS) {
                beersEntity.data?.let {
                    val favoritesBeer = beersRepository.getFavoriteBeers().beers

                    val mutableAllBeers = it.beers.toMutableList()
                    favoritesBeer.map { favoriteBeer ->
                        val beerUncheckedAsFavorite = mutableAllBeers.filter { noFavoriteBeer -> noFavoriteBeer.id == favoriteBeer.id }

                        if (beerUncheckedAsFavorite.isNotEmpty()) {
                            val beerToFavorite = beerUncheckedAsFavorite[0].apply { isFavorite = true }
                            mutableAllBeers.remove(beerUncheckedAsFavorite[0])
                            mutableAllBeers.add(beerToFavorite)
                        }
                    }

                    val sortedBeers = getSortedAscendingBeers(BeersEntity(mutableAllBeers.toList()))
                    beers = Result.success(sortedBeers)
                }
            } else {
                beers = Result.error(beersEntity.error)
            }
        }

        return beers
    }

    private fun getSortedAscendingBeers(beersEntity: BeersEntity): BeersEntity {
        return BeersEntity(beersEntity.beers.sortedBy { it.abv })
    }
}
