package com.example.manuel.baseproject.home.ui.adapterlist

import android.graphics.drawable.Drawable
import android.util.Log
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
        populateFavoriteIconView(beer.isFavorite)
    }

    private fun getAbv(abvId: String) = itemView.context.getString(R.string.abv, abvId)

    fun setOnClickListener(doOnFavoriteBeerSelected: ((BeerAdapterModel) -> Unit)?, beer: BeerAdapterModel) {
        itemView.setOnClickListener {
            beer.isFavorite = !beer.isFavorite
            populateFavoriteIconView(beer.isFavorite)
            doOnFavoriteBeerSelected?.invoke(beer)
        }
    }

    private fun populateFavoriteIconView(isFavorite: Boolean) {
        getFavoriteIcon(isFavorite)?.let { itemView.item_list_beer_favorite_button.background = it }
    }

    private fun getFavoriteIcon(isFavorite: Boolean): Drawable? {
        val yellowStar: Drawable? = itemView.context.getDrawable(R.drawable.ic_star_yellow_24dp)
        val whiteStar: Drawable? = itemView.context.getDrawable(R.drawable.ic_star_border_black_24dp)

        return if (isFavorite) yellowStar else whiteStar
    }
}
