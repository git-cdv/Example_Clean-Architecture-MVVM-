package com.example.manuel.baseproject.home.beers.domain.usecase

import android.util.Log
import com.example.manuel.baseproject.commons.datatype.Result
import com.example.manuel.baseproject.commons.datatype.ResultType
import com.example.manuel.baseproject.home.beers.domain.BeersRepository
import com.example.manuel.baseproject.home.beers.domain.model.BeersEntity

class GetBeersUseCase(private val beersRepository: BeersRepository) {

    // Refactor. Pedir las cervezas del server y las favoritas del fichero.  Modificar las del server con isFavorite a true las que correspondan y devolver.

    suspend fun execute(): Result<BeersEntity> {
        var beers: Result<BeersEntity> = Result.success(BeersEntity(listOf()))

        beersRepository.getAllBeers()?.let { beersEntity ->
            val resultType = beersEntity.resultType

            if (resultType == ResultType.SUCCESS) {
                beersEntity.data?.let {
                    // 1. Pido las favoritas
                    val favoritesBeer = beersRepository.getFavoriteBeers().beers


                    // 2. Cotejo las listas
                    val mutableAllBeers = it.beers.toMutableList()
                    favoritesBeer.mapIndexed { index, favoriteBeer ->
                        val beerUncheckedAsFavorite = mutableAllBeers.filter { noFavoriteBeer -> noFavoriteBeer.id == favoriteBeer.id }

                        if (beerUncheckedAsFavorite.isNotEmpty()) {
                            mutableAllBeers.remove(beerUncheckedAsFavorite[0])
                            mutableAllBeers.add(favoriteBeer)
                        }
                    }




                    // 2. Mapeo la entity
                    val sortedBeers = getSortedAscendingBeers(BeersEntity(mutableAllBeers.toList()))
                    beers = Result.success(sortedBeers)





                }

                // Insertar los favoritos en la lista, filtrar por misma id y borrar los de isFavorite false




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
