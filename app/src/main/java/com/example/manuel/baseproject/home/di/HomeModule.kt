package com.example.manuel.baseproject.home.di

import com.example.manuel.baseproject.home.beers.datasource.BeersNetworkDataSource
import com.example.manuel.baseproject.home.beers.datasource.retrofit.BeersApiService
import com.example.manuel.baseproject.home.beers.domain.BeersRepository
import com.example.manuel.baseproject.home.beers.domain.usecase.GetBeersUseCase
import com.example.manuel.baseproject.home.beers.domain.usecase.RemoveBeerUseCase
import com.example.manuel.baseproject.home.beers.domain.usecase.SaveBeerUseCase
import com.example.manuel.baseproject.home.beers.repository.BeersRepositoryImpl
import com.example.manuel.baseproject.home.beers.ui.adapterlist.BeersAdapter
import com.example.manuel.baseproject.home.beers.ui.adapterlist.model.BeerAdapterModel
import com.example.manuel.baseproject.home.beers.vm.HomeViewModel
import com.example.manuel.baseproject.home.beers.datasource.FavoritesCacheDataSource
import com.google.gson.GsonBuilder
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

object HomeModule {

    val beersModule = module {
        factory { provideBeersApiService(get()) }
        factory { BeersNetworkDataSource(beersApiService = get()) }
        single {
            BeersRepositoryImpl(
                    beersNetworkDataSource = get(),
                    favoritesCacheDataSource = get()
            ) as BeersRepository
        }
        factory { GetBeersUseCase(beersRepository = get()) }
        factory { SaveBeerUseCase(beersRepository = get()) }
        factory { RemoveBeerUseCase(beersRepository = get()) }
        viewModel {
            HomeViewModel(
                    getMealsByBeersUseCase = get(),
                    saveBeerUseCase = get(),
                    removeBeerUseCase = get()
            )
        }
        factory { (lambda: ((BeerAdapterModel) -> Unit)?) -> BeersAdapter(lambda) }
    }

    private fun provideBeersApiService(retrofit: Retrofit): BeersApiService {
        return retrofit.create(BeersApiService::class.java)
    }

    val favoritesModule = module {
        factory { GsonBuilder().setPrettyPrinting().create() }
        factory { FavoritesCacheDataSource(gson = get()) }
    }
}