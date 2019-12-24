package com.example.manuel.baseproject.home.di

import com.example.manuel.baseproject.home.beers.domain.usecase.GetBeersUseCase
import com.example.manuel.baseproject.home.beers.domain.usecase.RemoveBeerUseCase
import com.example.manuel.baseproject.home.beers.domain.usecase.SaveBeerUseCase
import com.example.manuel.baseproject.home.beers.ui.adapterlist.BeersAdapter
import com.example.manuel.baseproject.home.beers.ui.adapterlist.model.BeerAdapterModel
import com.example.manuel.baseproject.home.beers.vm.HomeViewModel
import com.example.manuel.baseproject.home.favorites.domain.GetFavoritesBeersUseCase
import com.example.manuel.baseproject.home.favorites.ui.adapterlist.FavoriteBeersAdapter
import com.example.manuel.baseproject.home.favorites.ui.adapterlist.model.FavoriteBeerAdapterModel
import com.example.manuel.baseproject.home.favorites.vm.FavoritesBeersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    factory { GetFavoritesBeersUseCase(repository = get()) }
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
    viewModel {
        FavoritesBeersViewModel(
                getFavoritesBeersUseCase = get(),
                removeBeerUseCase = get(),
                saveBeerUseCase = get()
        )
    }
    factory { (lambda: ((BeerAdapterModel) -> Unit)?) -> BeersAdapter(doOnFavoriteBeerSelected = lambda) }
    factory { (lambda: ((FavoriteBeerAdapterModel) -> Unit)?) -> FavoriteBeersAdapter(doOnFavoriteBeerSelected = lambda) }
}
