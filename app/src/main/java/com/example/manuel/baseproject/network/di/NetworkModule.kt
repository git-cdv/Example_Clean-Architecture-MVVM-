package com.example.manuel.baseproject.network.di

import com.example.manuel.baseproject.home.beers.domain.BeersRepository
import com.example.manuel.baseproject.home.beers.repository.BeersRepositoryImpl
import com.example.manuel.baseproject.network.data.datasource.api.BeersNetworkDataSource
import com.example.manuel.baseproject.network.data.datasource.api.retrofit.BeersApiService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val URL_BASE = "https://api.punkapi.com/v2/"

val retrofitModule = module {
    single { provideRetrofitInstance() }
}

private fun provideRetrofitInstance(): Retrofit = Retrofit.Builder()
        .baseUrl(URL_BASE)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

val beersApiModule = module {
    factory { provideBeersApiService(retrofit = get()) }
    factory { BeersNetworkDataSource(beersApiService = get()) }
    single {
        BeersRepositoryImpl(
                beersNetworkDataSource = get(),
                favoritesCacheDataSource = get()
        ) as BeersRepository
    }
}

private fun provideBeersApiService(retrofit: Retrofit): BeersApiService {
    return retrofit.create(BeersApiService::class.java)
}