package com.example.manuel.baseproject.home.favorites.ui.adapterlist

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.manuel.baseproject.R
import com.example.manuel.baseproject.commons.extensions.inflate
import com.example.manuel.baseproject.home.favorites.ui.adapterlist.model.FavoriteBeerAdapterModel

class FavoriteBeersAdapter(
        private val doOnFavoriteBeerSelected: ((FavoriteBeerAdapterModel) -> Unit)? = null
) : RecyclerView.Adapter<FavoriteViewHolder>() {

    private var beers: List<FavoriteBeerAdapterModel>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder =
            FavoriteViewHolder(parent.inflate(R.layout.item_list_beer))

    override fun getItemCount(): Int = beers?.size ?: 0

    override fun onBindViewHolder(viewHolder: FavoriteViewHolder, position: Int) {
        beers?.let {
            viewHolder.apply {
                populateViews(it[position])
                setOnClickListener(doOnFavoriteBeerSelected, it[position])
            }
        }
    }

    fun setData(beers: List<FavoriteBeerAdapterModel>) {
        this.beers = beers
    }
}
