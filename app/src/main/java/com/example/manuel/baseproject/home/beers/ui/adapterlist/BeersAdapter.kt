package com.example.manuel.baseproject.home.beers.ui.adapterlist

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.manuel.baseproject.R
import com.example.manuel.baseproject.view.extensions.inflate
import com.example.manuel.baseproject.home.beers.ui.adapterlist.model.BeerAdapterModel

class BeersAdapter(
        private val doOnFavoriteBeerSelected: ((BeerAdapterModel) -> Unit)
) : RecyclerView.Adapter<ViewHolder>() {

    private lateinit var beers: List<BeerAdapterModel>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(parent.inflate(R.layout.item_list_beer))

    override fun getItemCount(): Int = beers.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            populateViews(beers[position])
            setOnClickListener(doOnFavoriteBeerSelected, beers[position])
        }
    }

    fun updateAdapter(updatedList: List<BeerAdapterModel>) {
        val result = DiffUtil.calculateDiff(BeersDiffCallback(beers, updatedList))
        this.beers = updatedList.toMutableList()
        result.dispatchUpdatesTo(this)
    }

    fun setData(beers: List<BeerAdapterModel>) {
        this.beers = beers
    }
}
