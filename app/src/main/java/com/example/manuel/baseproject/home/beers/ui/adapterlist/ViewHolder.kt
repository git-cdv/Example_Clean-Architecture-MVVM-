package com.example.manuel.baseproject.home.beers.ui.adapterlist

import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.manuel.baseproject.R
import com.example.manuel.baseproject.commons.extensions.applyBackgroundColor
import com.example.manuel.baseproject.commons.extensions.loadImage
import com.example.manuel.baseproject.home.beers.ui.adapterlist.model.BeerAdapterModel
import kotlinx.android.synthetic.main.item_list_beer.view.*


class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun populateViews(beer: BeerAdapterModel) {
        itemView.item_list_beer_abv.text = getAbv(beer.abv.toString())
        itemView.item_list_beer_name.text = beer.name
        itemView.item_list_beer_tagline.text = beer.tagline
        itemView.item_list_beer_image.loadImage(beer.image)
        itemView.item_list_beer_abv.applyBackgroundColor(beer.abvColor)
        populateFavoriteIconView(beer.isFavorite, beer.abvColor)
    }

    private fun getAbv(abvId: String) = itemView.context.getString(R.string.abv, abvId)

    fun setOnClickListener(doOnFavoriteBeerSelected: ((BeerAdapterModel) -> Unit)?, beer: BeerAdapterModel) {
        itemView.setOnClickListener {
            beer.isFavorite = !beer.isFavorite
            populateFavoriteIconView(beer.isFavorite, beer.abvColor)
            doOnFavoriteBeerSelected?.invoke(beer)
        }
    }

    private fun populateFavoriteIconView(isFavorite: Boolean, abvColor: Int) {
        getFavoriteIcon(isFavorite, abvColor)?.let {
            itemView.item_list_beer_favorite_button.background = it
        }
    }

    private fun getFavoriteIcon(isFavorite: Boolean, abvColor: Int): Drawable? {
        return if (isFavorite) {
            when (abvColor) {
                R.color.green -> itemView.context.getDrawable(R.drawable.ic_star_green_24dp)
                R.color.orange -> itemView.context.getDrawable(R.drawable.ic_star_orange_24dp)
                else -> itemView.context.getDrawable(R.drawable.ic_star_red_24dp)
            }
        } else {
            itemView.context.getDrawable(R.drawable.ic_star_border_white_24dp)
        }
    }
}
