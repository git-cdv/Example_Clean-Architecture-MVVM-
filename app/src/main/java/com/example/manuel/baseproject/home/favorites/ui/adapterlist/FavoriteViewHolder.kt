package com.example.manuel.baseproject.home.favorites.ui.adapterlist

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.manuel.baseproject.R
import com.example.manuel.baseproject.home.favorites.ui.adapterlist.model.FavoriteBeerAdapterModel
import com.example.manuel.baseproject.view.extensions.applyBackgroundColor
import com.example.manuel.baseproject.view.extensions.loadImage
import kotlinx.android.synthetic.main.item_list_beer.view.*

class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun populateViews(beer: FavoriteBeerAdapterModel) {
        itemView.item_list_beer_abv.text = getAbv(beer.abv.toString())
        itemView.item_list_beer_name.text = beer.name
        itemView.item_list_beer_tagline.text = beer.tagline
        itemView.item_list_beer_image.loadImage(beer.image)
        itemView.item_list_beer_abv.applyBackgroundColor(beer.abvColor)
        itemView.item_list_beer_favorite_button.background =
                itemView.context.getDrawable(R.drawable.ic_star_white_24dp)
    }

    private fun getAbv(abvId: String) = itemView.context.getString(R.string.abv, abvId)
}
