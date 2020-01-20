package com.example.manuel.baseproject.data.repository

import com.example.manuel.baseproject.core.datatype.Result
import com.example.manuel.baseproject.core.datatype.ResultType
import com.example.manuel.baseproject.data.datasource.api.BeersNetworkDataSource
import com.example.manuel.baseproject.data.datasource.api.MAX_RESULTS_PER_PAGE
import com.example.manuel.baseproject.data.datasource.api.exceptions.BadRequestException
import com.example.manuel.baseproject.data.datasource.api.model.BeerApi
import com.example.manuel.baseproject.data.datasource.cache.BeersCacheDataSource
import com.example.manuel.baseproject.data.datasource.local.FavoritesLocalDataSource
import com.example.manuel.baseproject.data.datasource.local.model.BeerLocalModel
import com.example.manuel.baseproject.data.repository.mapper.ApiToEntityMapper
import com.example.manuel.baseproject.data.repository.mapper.ApiToLocalModelMapper
import com.example.manuel.baseproject.data.repository.mapper.LocalToEntityMapper
import com.example.manuel.baseproject.data.repository.mapper.EntityToLocalMapper
import com.example.manuel.baseproject.features.beers.domain.BeersRepository
import com.example.manuel.baseproject.features.beers.domain.model.BeerEntity
import com.example.manuel.baseproject.features.beers.domain.model.BeersEntity

class BeersRepositoryImpl(
        private val beersNetworkDataSource: BeersNetworkDataSource,
        private val favoritesLocalDataSource: FavoritesLocalDataSource,
        private val beersCacheDataSource: BeersCacheDataSource
) : BeersRepository {

    override suspend fun getAllBeers(): Result<BeersEntity>? {
        var page = -1
        var result: Result<BeersEntity>?
        val mutableBeers: MutableList<BeerApi> = mutableListOf()

        val allBeers: List<BeerLocalModel> = beersCacheDataSource.beers
        if (allBeers.isNotEmpty()) return Result.success(LocalToEntityMapper.map(allBeers))

        do {
            page = getPageToCheckBeers(page, mutableBeers.isNotEmpty(), mutableBeers.size)

            beersNetworkDataSource.getAllBeers(page.toString()).let { resultListBeerResponse ->
                if (resultListBeerResponse.resultType == ResultType.SUCCESS) {
                    resultListBeerResponse.data?.let {
                        mutableBeers.addAll(resultListBeerResponse.data)
                    }
                }

                result = if (resultListBeerResponse.resultType == ResultType.SUCCESS ||
                        (resultListBeerResponse.error is BadRequestException && mutableBeers.isNotEmpty())) {
                    Result.success(ApiToEntityMapper.map(mutableBeers.toList()))
                } else {
                    Result.error(resultListBeerResponse.error)
                }
            }
        } while (result?.resultType != Result.error<Error>().resultType && page != -1)

        if (result?.resultType == ResultType.SUCCESS) beersCacheDataSource.beers = ApiToLocalModelMapper.map(mutableBeers.toList())

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
        val beerCache = EntityToLocalMapper.map(beerEntity)
        return favoritesLocalDataSource.saveItem(beerCache)
    }

    override fun removeBeer(id: Int): Boolean {
        return favoritesLocalDataSource.removeItem(id)
    }

    override fun getFavoriteBeers(): BeersEntity {
        return LocalToEntityMapper.map(favoritesLocalDataSource.getItems())
    }
}
