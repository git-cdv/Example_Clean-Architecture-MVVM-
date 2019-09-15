package com.example.manuel.baseproject.home.datasource

import com.example.manuel.baseproject.home.commons.exceptions.BadRequestException
import com.example.manuel.baseproject.home.commons.exceptions.GenericNetworkException
import com.example.manuel.baseproject.home.commons.exceptions.NetworkConnectionException
import com.example.manuel.baseproject.home.datasource.mapper.NetworkMapper
import com.example.manuel.baseproject.home.datasource.model.api.BeersApi
import com.example.manuel.baseproject.home.datasource.utils.DataSourceBeersGenerator
import com.nhaarman.mockitokotlin2.mock
import okhttp3.ResponseBody
import org.junit.Assert
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.UnknownHostException

class NetworkMapperTest {

    @Test
    fun verifyMapperFromResponseModelToApiModel() {
        val givenBeersResponse = DataSourceBeersGenerator.getBeersResponse()

        val expectedResult: BeersApi = DataSourceBeersGenerator.getBeersApi()
        val realResult: BeersApi = NetworkMapper.ResponseToApiMapper.map(givenBeersResponse)

        Assert.assertEquals(expectedResult, realResult)
    }

    @Test
    fun verifySystemExceptionToCustomExceptionMapperWhenExceptionIsIOMustBeNetworkConnectionException() {
        verifyMapperSystemExceptionToCustomExceptionWhenSystemExceptionIs(
                givenException = IOException(),
                expectedException = NetworkConnectionException()
        )
    }

    @Test
    fun verifySystemExceptionToCustomExceptionMapperWhenExceptionIsUnknownHostMustBeNetworkConnectionException() {
        verifyMapperSystemExceptionToCustomExceptionWhenSystemExceptionIs(
                givenException = UnknownHostException(),
                expectedException = NetworkConnectionException()
        )
    }

    @Test
    fun verifySystemExceptionToCustomExceptionMapperWhenExceptionIsNotContemplatedMustBeGenericNetworkException() {
        verifyMapperSystemExceptionToCustomExceptionWhenSystemExceptionIs(
                givenException = Exception(),
                expectedException = GenericNetworkException()
        )
    }

    @Test
    fun verifySystemExceptionToCustomExceptionMapperWhenExceptionIsHttpExceptionCode400MustBeBadRequestException() {
        val httpException: HttpException = getHttpException(400)

        verifyMapperSystemExceptionToCustomExceptionWhenSystemExceptionIs(
                givenException = httpException,
                expectedException = BadRequestException()
        )
    }

    @Test
    fun verifySystemExceptionToCustomExceptionMapperWhenExceptionIsDifferent400MustBeGenericNetworkException() {
        val httpException: HttpException = mock()

        verifyMapperSystemExceptionToCustomExceptionWhenSystemExceptionIs(
                givenException = httpException,
                expectedException = GenericNetworkException()
        )
    }

    private fun getHttpException(code: Int): HttpException {
        return HttpException(
                Response.error<Exception>(
                        code,
                        ResponseBody.create(null, "")
                )
        )
    }

    private fun verifyMapperSystemExceptionToCustomExceptionWhenSystemExceptionIs(
            givenException: Exception,
            expectedException: Exception
    ) {

        val expectedResult: Exception = expectedException
        val realResult: Exception = NetworkMapper.SystemExceptionToCustomExceptionMapper.map(givenException)

        Assert.assertEquals(expectedResult::class, realResult::class)
    }
}