package com.example.manuel.baseproject.features.domain

import com.example.manuel.baseproject.features.beers.domain.usecase.GetBeersUseCase
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.runBlocking
import com.example.manuel.baseproject.core.datatype.Result
import com.example.manuel.baseproject.data.datasource.api.exceptions.NetworkConnectionException
import com.example.manuel.baseproject.features.beers.domain.BeersRepository
import com.example.manuel.baseproject.features.beers.domain.model.BeersEntity
import com.example.manuel.baseproject.features.domain.utils.DomainBeersGenerator
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert
import org.junit.Test
import java.lang.Exception

class GetBeersUseCaseTest {

    private val mockBeersRepository: BeersRepository = mock()
    private val getBeersUseCase = GetBeersUseCase(mockBeersRepository)

    @Test
    fun verifyBusinessErrorWhenRepoMockReturnNetworkError() {
        runBlocking {
            given(mockBeersRepository.getAllBeers())
                    .willReturn(Result.error(NetworkConnectionException()))

            val expectedResult = Result.error<Exception>(NetworkConnectionException()).error
            val realResult = getBeersUseCase.execute().error

            Assert.assertEquals(expectedResult is NetworkConnectionException, realResult is NetworkConnectionException)
        }
    }

    @Test
    fun verifyResultWhenRepoMockReturnSuccessState() {
        runBlocking {
            val result = Result.success(BeersEntity(listOf()))
            given(mockBeersRepository.getAllBeers()).willReturn(result)

            val expectedResult = Result.success(BeersEntity(listOf()))
            val realResult = getBeersUseCase.execute()

            Assert.assertEquals(expectedResult, realResult)
        }
    }

    @Test
    fun verifySortedAbvWhenRepoMockReturnUnsortedList() {
        runBlocking {
            val result = Result.success(DomainBeersGenerator.getUnsortedBeers())
            given(mockBeersRepository.getAllBeers()).willReturn(result)

            val expectedResultBeers = Result.success(DomainBeersGenerator.getSortedBeers()).data
            val realResultBeers = getBeersUseCase.execute().data

            realResultBeers?.beers?.forEachIndexed { index, currentRealResult ->
                val currentExpectedResult = expectedResultBeers?.beers?.get(index)?.abv
                val realResult = currentRealResult.abv

                Assert.assertEquals(realResult, currentExpectedResult)
            }
        }
    }

    @Test
    fun verifyUseCaseCallRepository() {
        runBlocking {
            given(mockBeersRepository.getAllBeers())
                    .willReturn(Result.success(BeersEntity(listOf())))

            getBeersUseCase.execute()

            verify(mockBeersRepository, times(1)).getAllBeers()
        }
    }
}
