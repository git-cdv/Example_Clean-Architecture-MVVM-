package com.example.manuel.baseproject.home.vm.mapper

import com.example.manuel.baseproject.home.commons.BaseMapper
import com.example.manuel.baseproject.home.vm.model.AbvColorType
import com.example.manuel.baseproject.home.domain.model.AbvRangeType
import com.example.manuel.baseproject.home.domain.model.BeerEntity
import com.example.manuel.baseproject.home.vm.model.BeerUI

object EntityToUIMapper : BaseMapper<List<BeerEntity>, List<BeerUI>> {
    override fun map(type: List<BeerEntity>?): List<BeerUI> {
        return type?.map {
            BeerUI(
                    id = it.id,
                    name = it.name,
                    tagline = it.tagline,
                    image = it.image,
                    abv = it.abv,
                    abvColorType = mapAbvType(it.getAbvRange(it.abv))
            )
        } ?: listOf()
    }

    private fun mapAbvType(abvRangeType: AbvRangeType): AbvColorType {
        return when (abvRangeType) {
            AbvRangeType.LOW -> AbvColorType.GREEN
            AbvRangeType.NORMAL -> AbvColorType.ORANGE
            AbvRangeType.HIGH -> AbvColorType.RED
        }
    }
}
