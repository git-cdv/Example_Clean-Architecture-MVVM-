package com.example.manuel.baseproject.features.di

import androidx.appcompat.widget.AppCompatImageView
import com.example.manuel.baseproject.features.beers.domain.usecase.GetBeersUseCase
import com.example.manuel.baseproject.features.beers.domain.usecase.RemoveBeerUseCase
import com.example.manuel.baseproject.features.beers.domain.usecase.SaveBeerUseCase
import com.example.manuel.baseproject.features.beers.ui.adapterlist.BeersAdapter
import com.example.manuel.baseproject.features.beers.ui.adapterlist.model.BeerAdapterModel
import com.example.manuel.baseproject.features.beers.vm.HomeViewModel
import com.example.manuel.baseproject.features.favorites.domain.GetFavoritesBeersUseCase
import com.example.manuel.baseproject.features.favorites.ui.adapterlist.FavoriteBeersAdapter
import com.example.manuel.baseproject.features.favorites.ui.adapterlist.model.FavoriteBeerAdapterModel
import com.example.manuel.baseproject.features.favorites.vm.FavoritesBeersViewModel
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
    factory { (favoriteBeerListener: (BeerAdapterModel) -> Unit, beerDetailListener: (BeerAdapterModel, AppCompatImageView) -> Unit) ->
        BeersAdapter(favoriteBeerListener = favoriteBeerListener, beerDetailListener = beerDetailListener)
    }
    factory { (beerDetailListener: (FavoriteBeerAdapterModel, AppCompatImageView) -> Unit, favoriteBeerItemListener: (FavoriteBeerAdapterModel) -> Unit) ->
        FavoriteBeersAdapter(beerDetailListener = beerDetailListener, favoriteBeerItemListener = favoriteBeerItemListener)
    }
}
