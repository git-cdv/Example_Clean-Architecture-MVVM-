package com.example.manuel.baseproject.features.favorites.ui.adapterlist

import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.manuel.baseproject.R
import com.example.manuel.baseproject.features.favorites.ui.adapterlist.model.FavoriteBeerAdapterModel
import com.example.manuel.baseproject.view.extensions.inflate
import kotlinx.android.synthetic.main.item_list_beer.view.*

class FavoriteBeersAdapter(
        private val beerDetailListener: (FavoriteBeerAdapterModel, AppCompatImageView) -> Unit,
        private val favoriteBeerItemListener: (FavoriteBeerAdapterModel) -> Unit
) : RecyclerView.Adapter<FavoriteViewHolder>() {

    private lateinit var beers: List<FavoriteBeerAdapterModel>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = parent.inflate(R.layout.item_list_beer)
        val viewHolder = FavoriteViewHolder(view)

        setItemViewListener(viewHolder)
        setFavoriteIconListener(viewHolder)

        return viewHolder
    }

    private fun setItemViewListener(viewHolder: FavoriteViewHolder) {
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val beerImage = viewHolder.itemView.item_list_beer_image
                beerDetailListener.invoke(beers[position], beerImage)
            }
        }
    }

    private fun setFavoriteIconListener(viewHolder: FavoriteViewHolder) {
        viewHolder.itemView.item_list_beer_favorite_button.setOnClickListener {
            val position = viewHolder.adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                favoriteBeerItemListener.invoke(beers[position])
            }
        }
    }

    override fun getItemCount(): Int = beers.size

    override fun onBindViewHolder(viewHolder: FavoriteViewHolder, position: Int) {
        viewHolder.populateViews(beers[position])
    }

    fun setData(beers: List<FavoriteBeerAdapterModel>) {
        this.beers = beers
    }
}
