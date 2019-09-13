package com.example.manuel.baseproject.datasource.mapper

import com.example.manuel.baseproject.commons.BaseMapper
import com.example.manuel.baseproject.commons.exceptions.GenericNetworkException
import com.example.manuel.baseproject.commons.exceptions.NetworkConnectionException
import com.example.manuel.baseproject.commons.exceptions.datasource.BadRequestException
import com.example.manuel.baseproject.datasource.model.api.BeerApi
import com.example.manuel.baseproject.datasource.model.response.BeerResponse
import com.example.manuel.baseproject.datasource.model.api.BeersApi
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException

class NetworkMapper {

    object ResponseToApiMapper : BaseMapper<List<BeerResponse>, BeersApi> {
        override fun map(type: List<BeerResponse>?): BeersApi {
            return BeersApi(type?.map {
                BeerApi(
                        id = it.id ?: -1,
                        name = it.name ?: "",
                        tagline = it.tagline ?: "",
                        image = it.image ?: "",
                        abv = it.abv ?: -1.0
                )
            } ?: listOf())
        }
    }

    object ExceptionToErrorMapper : BaseMapper<Exception, Exception> {
        override fun map(type: Exception?): Exception {
            return when (type) {
                is IOException -> NetworkConnectionException()
                is UnknownHostException -> NetworkConnectionException()
                is HttpException -> apiErrorFromCodeException(type.code())
                else -> GenericNetworkException()
            }
        }
    }

    companion object {
        private fun apiErrorFromCodeException(code: Int): Exception {
            return if (code == 400) {
                BadRequestException()
            } else {
                GenericNetworkException()
            }
        }
    }
}
