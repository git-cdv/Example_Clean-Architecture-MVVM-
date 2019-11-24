package com.example.manuel.baseproject.home.beers.ui.mapper

import android.util.Log
import com.example.manuel.baseproject.R
import com.example.manuel.baseproject.commons.BaseMapper
import com.example.manuel.baseproject.home.beers.ui.adapterlist.model.BeerAdapterModel
import com.example.manuel.baseproject.home.beers.vm.model.AbvColorType
import com.example.manuel.baseproject.home.beers.vm.model.BeerUI

object BeerUIToAdapterModelMapper : BaseMapper<List<BeerUI>, List<BeerAdapterModel>> {

    override fun map(type: List<BeerUI>?): List<BeerAdapterModel> {
        val favoritesInMapUI = type?.filter { it.isFavorite }
        Log.i("test", "favoritesInMapUi size = ${favoritesInMapUI?.size}")

        return type?.map {
            BeerAdapterModel(
                    id = it.id,
                    name = it.name,
                    tagline = it.tagline,
                    image = it.image,
                    abv = it.abv,
                    abvColor = getColor(it.abvColorType),
                    isFavorite = it.isFavorite
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
                isFavorite = type.isFavorite
        )
    }

    private fun getColorType(abvColor: Int): AbvColorType {
        return when (abvColor) {
            R.color.green -> AbvColorType.GREEN
            R.color.orange -> AbvColorType.ORANGE
            else -> AbvColorType.RED
        }
    }
}
