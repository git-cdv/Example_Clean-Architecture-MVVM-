package com.example.manuel.baseproject.home.datasource

import com.example.manuel.baseproject.network.handleNetworkExceptions
import com.example.manuel.baseproject.network.exceptions.BadRequestException
import com.example.manuel.baseproject.network.exceptions.GenericNetworkException
import com.example.manuel.baseproject.network.exceptions.NetworkConnectionException
import com.nhaarman.mockitokotlin2.mock
import io.kotlintest.matchers.types.shouldBeInstanceOf
import io.kotlintest.specs.BehaviorSpec
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.UnknownHostException
import okhttp3.ResponseBody


class HandlerNetworkExceptionsstBDD : BehaviorSpec({

    Given("an IOException") {
        val exception = IOException()

        When("the handleNetworkExceptions method is executed") {
            val result = handleNetworkExceptions(exception)

            Then("the result must be a NetworkConnectionException") {
                result.shouldBeInstanceOf<NetworkConnectionException>()
            }
        }
    }

    Given("an UnknownHostException") {
        val exception = UnknownHostException()

        When("the handleNetworkExceptions method is executed") {
            val result = handleNetworkExceptions(exception)

            Then("the result must be a NetworkConnectionException") {
                result.shouldBeInstanceOf<NetworkConnectionException>()
            }
        }
    }

    Given("an another exception") {
        val exception = Exception()

        When("the handleNetworkExceptions method is executed") {
            val result = handleNetworkExceptions(exception)

            Then("the result must be a GenericNetworkException") {
                result.shouldBeInstanceOf<GenericNetworkException>()
            }
        }
    }

    Given("a HttpException with code 400") {
        val responseBody: ResponseBody = mock()
        val responseHttpException = Response.error<HttpException>(400, responseBody)
        val httpExceptionCode400 = HttpException(responseHttpException)

        When("the handleNetworkExceptions method is executed") {
            val realResult = handleNetworkExceptions(httpExceptionCode400)

            Then("the result must be a BadRequestException") {
                realResult.shouldBeInstanceOf<BadRequestException>()
            }
        }
    }

    Given("a HttpException with random error code") {
        val responseBody: ResponseBody = mock()
        val randomErrorCode = (401..599).random()
        val responseHttpException = Response.error<HttpException>(randomErrorCode, responseBody)
        val httpExceptionRandomErrorCode = HttpException(responseHttpException)

        When("the handleNetworkExceptions method is executed") {
            val realResult = handleNetworkExceptions(httpExceptionRandomErrorCode)

            Then("the result must be a GenericNetworkException") {
                realResult.shouldBeInstanceOf<GenericNetworkException>()
            }
        }
    }
})