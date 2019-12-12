package com.example.manuel.baseproject.home.datasource

import com.example.manuel.baseproject.data.datasource.api.exceptions.handleNetworkExceptions
import com.example.manuel.baseproject.data.datasource.api.exceptions.BadRequestException
import com.example.manuel.baseproject.data.datasource.api.exceptions.GenericNetworkException
import com.example.manuel.baseproject.data.datasource.api.exceptions.NetworkConnectionException
import com.example.manuel.baseproject.data.datasource.api.mapper.ResponseToApiMapper
import com.example.manuel.baseproject.data.datasource.api.model.api.BeersApi
import com.example.manuel.baseproject.home.datasource.utils.DataSourceBeersGenerator
import com.nhaarman.mockitokotlin2.mock
import okhttp3.ResponseBody
import org.junit.Assert
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.UnknownHostException

class BeersNetworkMapperTest {

    @Test
    fun verifyMapperFromResponseModelToApiModel() {
        val givenBeersResponse = DataSourceBeersGenerator.getBeersResponse()

        val expectedResult: BeersApi = DataSourceBeersGenerator.getBeersApi()
        val realResult: BeersApi = ResponseToApiMapper.map(givenBeersResponse)

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

    private fun getHttpException(code: Int): HttpException {
        return HttpException(
                Response.error<Exception>(
                        code,
                        ResponseBody.create(null, "")
                )
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

    private fun verifyMapperSystemExceptionToCustomExceptionWhenSystemExceptionIs(
            givenException: Exception,
            expectedException: Exception
    ) {
        val expectedResult: Exception = expectedException
        val realResult: Exception = handleNetworkExceptions(givenException)

        Assert.assertEquals(expectedResult::class, realResult::class)
    }
}