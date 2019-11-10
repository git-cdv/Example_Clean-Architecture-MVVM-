package com.example.manuel.baseproject.home.repository

import com.example.manuel.baseproject.home.commons.datatype.Result
import com.example.manuel.baseproject.home.commons.datatype.ResultType
import com.example.manuel.baseproject.home.commons.exceptions.NetworkConnectionException
import com.example.manuel.baseproject.home.commons.exceptions.BadRequestException
import com.example.manuel.baseproject.home.domain.BeersRepository
import com.example.manuel.baseproject.home.domain.model.BeerEntity
import com.example.manuel.baseproject.home.datasource.BeersNetworkDataSource
import com.example.manuel.baseproject.home.datasource.MAX_RESULTS_PER_PAGE
import com.example.manuel.baseproject.home.datasource.model.api.BeersApi
import com.example.manuel.baseproject.home.domain.model.BeersEntity
import com.example.manuel.baseproject.home.repository.mapper.ApiToEntityMapper

class BeersRepositoryImpl constructor(
        private val beersNetworkDataSource: BeersNetworkDataSource
) : BeersRepository {

    private val beers = mutableListOf<BeerEntity>()

    override suspend fun getAllBeers(): Result<BeersEntity>? {
        var page = -1

        var result: Result<BeersEntity>?

        do {
            page = getPageToCheckBeers(page)

            beersNetworkDataSource.getAllBeers(page.toString()).let { resultListBeerResponse ->
                addAllBeersUntilLastPage(resultListBeerResponse)
                result = initResult(resultListBeerResponse)
            }
        } while (result?.resultType != Result.error<Error>().resultType && beers.size == 0)

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

    private fun hasBeers() = beers.size > 0

    private fun isNecessaryFetchMoreBeers(page: Int): Boolean {
        return (beers.size / page) == MAX_RESULTS_PER_PAGE
    }

    private fun addAllBeersUntilLastPage(beersApiResult: Result<BeersApi>) {
        ApiToEntityMapper.map(beersApiResult.data).let { beersEntity ->
            beersEntity.beers.forEach { beerEntity ->
                beers.add(beerEntity)
            }
        }
    }

    private fun initResult(beersApiResult: Result<BeersApi>): Result<BeersEntity> {
        return if (beersApiResult.resultType == ResultType.SUCCESS) {
            Result.success(BeersEntity(beers))
        } else {
            if (hasNotMoreBeers(beersApiResult.error)) {
                Result.success(BeersEntity(beers))
            } else {
                Result.error(NetworkConnectionException())
            }
        }
    }

    private fun hasNotMoreBeers(error: Exception?): Boolean {
        return beers.isNotEmpty() && error == BadRequestException()
    }
}
