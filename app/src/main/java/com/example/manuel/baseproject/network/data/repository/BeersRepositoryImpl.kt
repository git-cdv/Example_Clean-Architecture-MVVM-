package com.example.manuel.baseproject.network.data.repository

import com.example.manuel.baseproject.core.datatype.Result
import com.example.manuel.baseproject.core.datatype.ResultType
import com.example.manuel.baseproject.network.exceptions.BadRequestException
import com.example.manuel.baseproject.network.data.datasource.api.BeersNetworkDataSource
import com.example.manuel.baseproject.network.data.datasource.cache.FavoritesCacheDataSource
import com.example.manuel.baseproject.network.data.datasource.api.MAX_RESULTS_PER_PAGE
import com.example.manuel.baseproject.network.data.datasource.api.model.api.BeerApi
import com.example.manuel.baseproject.network.data.datasource.api.model.api.BeersApi
import com.example.manuel.baseproject.home.beers.domain.BeersRepository
import com.example.manuel.baseproject.home.beers.domain.model.BeerEntity
import com.example.manuel.baseproject.home.beers.domain.model.BeersEntity
import com.example.manuel.baseproject.network.data.repository.mapper.ApiToEntityMapper
import com.example.manuel.baseproject.network.data.repository.mapper.CacheToEntityMapper
import com.example.manuel.baseproject.network.data.repository.mapper.EntityToCacheMapper

class BeersRepositoryImpl(
        private val beersNetworkDataSource: BeersNetworkDataSource,
        private val favoritesCacheDataSource: FavoritesCacheDataSource
) : BeersRepository {

    override suspend fun getAllBeers(): Result<BeersEntity>? {
        var page = -1
        var result: Result<BeersEntity>?
        val mutableBeers: MutableList<BeerApi> = mutableListOf()

        do {
            page = getPageToCheckBeers(page, mutableBeers.isNotEmpty(), mutableBeers.size)

            beersNetworkDataSource.getAllBeers(page.toString()).let { resultListBeerResponse ->
                if (resultListBeerResponse.resultType == ResultType.SUCCESS) {
                    resultListBeerResponse.data?.let {
                        mutableBeers.addAll(resultListBeerResponse.data.beers)
                    }
                }

                result = if (resultListBeerResponse.resultType == ResultType.SUCCESS ||
                        (resultListBeerResponse.error is BadRequestException && mutableBeers.isNotEmpty())) {
                    Result.success(ApiToEntityMapper.map(BeersApi(mutableBeers.toList())))
                } else {
                    Result.error(resultListBeerResponse.error)
                }
            }
        } while (result?.resultType != Result.error<Error>().resultType && page != -1)


        return result
    }

    private fun getPageToCheckBeers(currentPage: Int, isMutableBeersNotEmpty: Boolean, beersSize: Int): Int {
        var page: Int = currentPage

        if (isMutableBeersNotEmpty) {
            if (isNecessaryFetchMoreBeers(currentPage, beersSize)) page++ else page = -1
        } else {
            page = 1
        }

        return page
    }

    private fun isNecessaryFetchMoreBeers(page: Int, beersSize: Int): Boolean {
        return (beersSize / page) == MAX_RESULTS_PER_PAGE
    }

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
