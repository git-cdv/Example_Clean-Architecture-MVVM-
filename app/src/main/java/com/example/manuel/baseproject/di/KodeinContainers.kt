package com.example.manuel.baseproject.di

import com.example.manuel.baseproject.domain.usecase.GetBeersUseCase
import com.example.manuel.baseproject.domain.MealsByBeersRepository
import com.example.manuel.baseproject.repository.MealsByBeersRepositoryImpl
import com.example.manuel.baseproject.datasource.MealsByBeersNetworkDataSource
import com.example.manuel.baseproject.datasource.retrofit.RetrofitConfiguration
import com.example.manuel.baseproject.vm.MealsByBeersViewModel
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import kotlinx.coroutines.ExperimentalCoroutinesApi

class KodeinContainers {

    companion object {
        @ExperimentalCoroutinesApi
        val diBaseProject = Kodein {
            bind<RetrofitConfiguration>() with provider { RetrofitConfiguration() }
            bind<MealsByBeersNetworkDataSource>() with provider { MealsByBeersNetworkDataSource(instance()) }
            bind<MealsByBeersRepository>() with provider { MealsByBeersRepositoryImpl(instance()) }
            bind<GetBeersUseCase>() with provider { GetBeersUseCase(instance()) }
            bind<MealsByBeersViewModel>() with provider { MealsByBeersViewModel(instance()) }
        }
    }
}