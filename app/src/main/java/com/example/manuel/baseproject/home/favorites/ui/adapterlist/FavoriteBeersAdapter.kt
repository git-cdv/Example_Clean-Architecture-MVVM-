package com.example.manuel.baseproject.home.favorites.ui.adapterlist

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.manuel.baseproject.R
import com.example.manuel.baseproject.view.extensions.inflate
import com.example.manuel.baseproject.home.favorites.ui.adapterlist.model.FavoriteBeerAdapterModel

class FavoriteBeersAdapter(
        private val favoriteBeerListener: ((FavoriteBeerAdapterModel) -> Unit)
) : RecyclerView.Adapter<FavoriteViewHolder>() {

    private lateinit var beers: List<FavoriteBeerAdapterModel>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = parent.inflate(R.layout.item_list_beer)
        val viewHolder = FavoriteViewHolder(view)

        setItemViewListener(viewHolder)

        return viewHolder
    }

    private fun setItemViewListener(viewHolder: FavoriteViewHolder) {
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                favoriteBeerListener.invoke(beers[position])
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
