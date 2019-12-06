package com.example.manuel.baseproject.home.beers.repository

import com.example.manuel.baseproject.commons.datatype.Result
import com.example.manuel.baseproject.commons.datatype.ResultType
import com.example.manuel.baseproject.commons.exceptions.BadRequestException
import com.example.manuel.baseproject.commons.exceptions.NetworkConnectionException
import com.example.manuel.baseproject.home.beers.datasource.BeersNetworkDataSource
import com.example.manuel.baseproject.home.beers.datasource.FavoritesCacheDataSource
import com.example.manuel.baseproject.home.beers.datasource.MAX_RESULTS_PER_PAGE
import com.example.manuel.baseproject.home.beers.datasource.model.api.BeersApi
import com.example.manuel.baseproject.home.beers.domain.BeersRepository
import com.example.manuel.baseproject.home.beers.domain.model.BeerEntity
import com.example.manuel.baseproject.home.beers.domain.model.BeersEntity
import com.example.manuel.baseproject.home.beers.repository.mapper.ApiToEntityMapper
import com.example.manuel.baseproject.home.beers.repository.mapper.CacheToEntityMapper
import com.example.manuel.baseproject.home.beers.repository.mapper.EntityToCacheMapper

class BeersRepositoryImpl(
        private val beersNetworkDataSource: BeersNetworkDataSource,
        private val favoritesCacheDataSource: FavoritesCacheDataSource
) : BeersRepository {

    private lateinit var mutableBeers: MutableList<BeerEntity>

    override suspend fun getAllBeers(): Result<BeersEntity>? {
        mutableBeers = mutableListOf()
        var page = -1
        var result: Result<BeersEntity>?

        do {
            page = getPageToCheckBeers(page)

            beersNetworkDataSource.getAllBeers(page.toString()).let { resultListBeerResponse ->
                addAllBeersUntilLastPage(resultListBeerResponse)
                result = initResult(resultListBeerResponse)
            }
        } while (result?.resultType != Result.error<Error>().resultType && page != -1)

        return result
    }

    private fun getPageToCheckBeers(currentPage: Int): Int {
        var page: Int = currentPage

        if (hasBeers()) {
            if (isNecessaryFetchMoreBeers(currentPage)) page++ else page = -1
        } else {
            page = 1
        }

        return page
    }

    private fun hasBeers() = mutableBeers.size > 0

    private fun isNecessaryFetchMoreBeers(page: Int): Boolean {
        return (mutableBeers.size / page) == MAX_RESULTS_PER_PAGE
    }

    private fun addAllBeersUntilLastPage(beersApiResult: Result<BeersApi>) {
        ApiToEntityMapper.map(beersApiResult.data).let { beersEntity ->
            beersEntity.beers.forEach { beerEntity ->
                mutableBeers.add(beerEntity)
            }
        }
    }

    private fun initResult(beersApiResult: Result<BeersApi>): Result<BeersEntity> {
        return if (
                beersApiResult.resultType == ResultType.SUCCESS ||
                beersApiResult.error is BadRequestException && mutableBeers.size > 0
        ) {
            Result.success(BeersEntity(mutableBeers))
        } else {
            Result.error(NetworkConnectionException())
        }
    }

    // ESTOS TRES MÉTODOS ESTÁN PERFECTOS, DEVUELVEN LO QUE TIENEN QUE DEVOLVER
    override fun saveBeer(beerEntity: BeerEntity): Boolean {
        val beerCache = EntityToCacheMapper.map(beerEntity)
        return favoritesCacheDataSource.saveBeer(beerCache)
    }

    override fun removeBeer(id: Int): Boolean {
        return favoritesCacheDataSource.removeBeer(id)
    }

    override fun getFavoriteBeers(): BeersEntity {
        return CacheToEntityMapper.map(favoritesCacheDataSource.getBeers())
    }
}
