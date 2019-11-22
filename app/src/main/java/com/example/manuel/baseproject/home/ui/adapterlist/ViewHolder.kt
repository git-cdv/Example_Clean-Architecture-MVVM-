package com.example.manuel.baseproject.home.ui.adapterlist

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.manuel.baseproject.R
import com.example.manuel.baseproject.home.commons.extensions.applyBackgroundColor
import com.example.manuel.baseproject.home.commons.extensions.loadImage
import com.example.manuel.baseproject.home.ui.adapterlist.model.BeerAdapterModel
import kotlinx.android.synthetic.main.item_list_beer.view.*

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun populateViews(beer: BeerAdapterModel) {
        itemView.item_list_beer_abv.text = getAbv(beer.abv.toString())
        itemView.item_list_beer_name.text = beer.name
        itemView.item_list_beer_tagline.text = beer.tagline
        itemView.item_list_beer_image.loadImage(beer.image)
        itemView.item_list_beer_abv.applyBackgroundColor(beer.abvColor)
    }

    private fun getAbv(abvId: String) = itemView.context.getString(R.string.abv, abvId)

    fun setOnClickListener(doOnFavoriteBeerSelected: ((BeerAdapterModel) -> Unit)?, beer: BeerAdapterModel) {
        itemView.setOnClickListener {
            doOnFavoriteBeerSelected?.invoke(beer)
        }
    }
}
