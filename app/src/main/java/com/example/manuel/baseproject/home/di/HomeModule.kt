package com.example.manuel.baseproject.home.di

import android.content.Context
import com.example.manuel.baseproject.cache.CacheDataSource
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
import java.io.File

private const val FILE_FAVORITES_BEERS = "FavoritesBeers.txt"

val beersModule = module {
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
    factory { (lambda: ((BeerAdapterModel) -> Unit)?) -> BeersAdapter(doOnFavoriteBeerSelected = lambda) }
    factory { (lambda: ((FavoriteBeerAdapterModel) -> Unit)?) -> FavoriteBeersAdapter(doOnFavoriteBeerSelected = lambda) }
}

val favoritesModule = module {
    factory { CacheDataSource(gson = get(), file = provideFavoritesBeersFile(context = get())) }
    factory { GetFavoritesBeersUseCase(repository = get()) }
    factory {
        FavoritesBeersViewModel(
                getFavoritesBeersUseCase = get(),
                removeBeerUseCase = get(),
                saveBeerUseCase = get()
        )
    }
}

private fun provideFavoritesBeersFile(context: Context): File {
    val filePath: String = context.filesDir.path.toString() + "/$FILE_FAVORITES_BEERS"
    return File(filePath)
}
