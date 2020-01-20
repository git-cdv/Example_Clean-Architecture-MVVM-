package com.example.manuel.baseproject.features.beers.domain.usecase

import com.example.manuel.baseproject.core.datatype.Result
import com.example.manuel.baseproject.core.datatype.ResultType
import com.example.manuel.baseproject.features.beers.domain.BeersRepository
import com.example.manuel.baseproject.features.beers.domain.model.BeerEntity
import com.example.manuel.baseproject.features.beers.domain.model.BeersEntity

class GetBeersUseCase(private val beersRepository: BeersRepository) {

    suspend fun execute(): Result<BeersEntity> {
        var beers: Result<BeersEntity> = Result.success(BeersEntity(listOf()))

        beersRepository.getAllBeers()?.let { beersEntity ->
            val resultType = beersEntity.resultType

            if (resultType == ResultType.SUCCESS) {
                beersEntity.data?.let {
                    val allBeersWithFavoritesChecked =
                            getAllBeersWithFavoritesChecked(it.beers.toMutableList()).toList()

                    val sortedBeers =
                            getSortedAscendingBeers(BeersEntity(allBeersWithFavoritesChecked))

                    beers = Result.success(sortedBeers)
                }
            } else {
                beers = Result.error(beersEntity.error)
            }
        }

        return beers
    }

    private fun getAllBeersWithFavoritesChecked(mutableAllBeers: MutableList<BeerEntity>): MutableList<BeerEntity> {
        val favoritesBeer = beersRepository.getFavoriteBeers().beers
        favoritesBeer.map { favoriteBeer ->
            val beerUncheckedAsFavorite =
                    mutableAllBeers.filter { noFavoriteBeer -> noFavoriteBeer.id == favoriteBeer.id }

            if (beerUncheckedAsFavorite.isNotEmpty()) {
                val beerToFavorite = beerUncheckedAsFavorite[0].apply { isFavorite = true }
                mutableAllBeers.remove(beerUncheckedAsFavorite[0])
                mutableAllBeers.add(beerToFavorite)
            }
        }

        return mutableAllBeers
    }

    private fun getSortedAscendingBeers(beersEntity: BeersEntity): BeersEntity {
        return BeersEntity(beersEntity.beers.sortedBy { it.abv })
    }
}
