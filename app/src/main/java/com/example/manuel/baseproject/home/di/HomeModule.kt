package com.example.manuel.baseproject.home.di

import com.example.manuel.baseproject.home.datasource.BeersNetworkDataSource
import com.example.manuel.baseproject.home.datasource.retrofit.BeersApiService
import com.example.manuel.baseproject.home.domain.BeersRepository
import com.example.manuel.baseproject.home.domain.usecase.GetBeersUseCase
import com.example.manuel.baseproject.home.repository.BeersRepositoryImpl
import com.example.manuel.baseproject.home.ui.adapterlist.BeersAdapter
import com.example.manuel.baseproject.home.vm.HomeViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

@ExperimentalCoroutinesApi
object HomeModule {

    val mainModule = module {
        factory { provideBeersApiService(get()) }
        factory { BeersNetworkDataSource(beersApiService = get()) }
        factory { BeersRepositoryImpl(beersNetworkDataSource = get()) as BeersRepository }
        factory { GetBeersUseCase(beersRepository = get()) }
        viewModel { HomeViewModel(getMealsByBeersUseCase = get()) }
        factory { BeersAdapter(context = get()) }
    }

    private fun provideBeersApiService(retrofit: Retrofit): BeersApiService {
        return retrofit.create(BeersApiService::class.java)
    }
}