package com.example.manuel.baseproject.network.di

import android.content.Context
import com.example.manuel.baseproject.home.beers.domain.BeersRepository
import com.example.manuel.baseproject.network.data.datasource.api.BeersNetworkDataSource
import com.example.manuel.baseproject.network.data.datasource.cache.FavoritesCacheDataSource
import com.example.manuel.baseproject.network.data.datasource.api.retrofit.BeersApiService
import com.example.manuel.baseproject.network.data.repository.BeersRepositoryImpl
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

private const val URL_BASE = "https://api.punkapi.com/v2/"
private const val FILE_FAVORITES_BEERS = "FavoritesBeers.txt"

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

val favoritesCacheModule = module {
    factory { provideFavoritesBeersFile(context = get()) }
    factory { GsonBuilder().setPrettyPrinting().create() }
    factory { FavoritesCacheDataSource(gson = get(), favoritesBeersFile = get()) }
}

private fun provideFavoritesBeersFile(context: Context): File {
    val filePath: String = context.filesDir.path.toString() + "/$FILE_FAVORITES_BEERS"
    return File(filePath)
}

