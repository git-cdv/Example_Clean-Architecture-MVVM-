package com.example.manuel.baseproject.features.beers.ui.mapper

import com.example.manuel.baseproject.R
import com.example.manuel.baseproject.core.BaseMapper
import com.example.manuel.baseproject.features.beers.ui.adapterlist.model.BeerAdapterModel
import com.example.manuel.baseproject.features.favorites.ui.adapterlist.model.FavoriteBeerAdapterModel
import com.example.manuel.baseproject.features.beers.vm.model.AbvColorType
import com.example.manuel.baseproject.features.beers.vm.model.BeerUI

object BeerUIToAdapterModelMapper : BaseMapper<List<BeerUI>, List<BeerAdapterModel>> {

    override fun map(type: List<BeerUI>?): List<BeerAdapterModel> {
        return type?.map {
            BeerAdapterModel(
                    id = it.id,
                    name = it.name,
                    tagline = it.tagline,
                    image = it.image,
                    abv = it.abv,
                    abvColor = getColor(it.abvColorType),
                    isFavorite = it.isFavorite,
                    foodPairing = it.foodPairing
            )
        } ?: listOf()
    }

    private fun getColor(abvType: AbvColorType): Int {
        return when (abvType) {
            AbvColorType.GREEN -> R.color.green
            AbvColorType.ORANGE -> R.color.orange
            else -> R.color.red
        }
    }
}

object BeerAdapterModelToBeerUIMapper : BaseMapper<BeerAdapterModel, BeerUI> {
    override fun map(type: BeerAdapterModel?): BeerUI {
        return BeerUI(
                id = type!!.id,
                name = type.name,
                tagline = type.tagline,
                image = type.image,
                abv = type.abv,
                abvColorType = getColorType(type.abvColor),
                isFavorite = type.isFavorite,
                foodPairing = type.foodPairing
        )
    }
}

object BeerUIToFavoriteAdapterModelMapper : BaseMapper<List<BeerUI>, List<FavoriteBeerAdapterModel>> {

    override fun map(type: List<BeerUI>?): List<FavoriteBeerAdapterModel> {
        return type?.map {
            FavoriteBeerAdapterModel(
                    id = it.id,
                    name = it.name,
                    tagline = it.tagline,
                    image = it.image,
                    abv = it.abv,
                    abvColor = getColor(it.abvColorType),
                    isFavorite = it.isFavorite,
                    foodPairing = it.foodPairing
            )
        } ?: listOf()
    }
}

private fun getColor(abvType: AbvColorType): Int {
    return when (abvType) {
        AbvColorType.GREEN -> R.color.green
        AbvColorType.ORANGE -> R.color.orange
        else -> R.color.red
    }
}

object FavoriteBeerAdapterModelToBeerUIMapper : BaseMapper<FavoriteBeerAdapterModel, BeerUI> {
    override fun map(type: FavoriteBeerAdapterModel?): BeerUI {
        return BeerUI(
                id = type!!.id,
                name = type.name,
                tagline = type.tagline,
                image = type.image,
                abv = type.abv,
                abvColorType = getColorType(type.abvColor),
                isFavorite = type.isFavorite,
                foodPairing = type.foodPairing
        )
    }
}

private fun getColorType(abvColor: Int): AbvColorType {
    return when (abvColor) {
        R.color.green -> AbvColorType.GREEN
        R.color.orange -> AbvColorType.ORANGE
        else -> AbvColorType.RED
    }
}