package com.example.manuel.baseproject.home.ui.mapper

import com.example.manuel.baseproject.R
import com.example.manuel.baseproject.home.commons.BaseMapper
import com.example.manuel.baseproject.home.ui.adapterlist.model.BeerAdapterModel
import com.example.manuel.baseproject.home.vm.model.AbvColorType
import com.example.manuel.baseproject.home.vm.model.BeerUI

object BeerUIToAdapterModelMapper : BaseMapper<List<BeerUI>, List<BeerAdapterModel>> {

    override fun map(type: List<BeerUI>?): List<BeerAdapterModel> {
        return type?.map {
            BeerAdapterModel(
                    id = it.id,
                    name = it.name,
                    tagline = it.tagline,
                    image = it.image,
                    abv = it.abv,
                    abvColor = getColor(it.abvColorType)
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
