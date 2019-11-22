package com.example.manuel.baseproject.home.ui.adapterlist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.manuel.baseproject.R
import com.example.manuel.baseproject.home.ui.adapterlist.model.BeerAdapterModel

class BeersAdapter(
        private val context: Context,
        private val doOnFavoriteBeerSelected: ((BeerAdapterModel) -> Unit)? = null
) : RecyclerView.Adapter<ViewHolder>() {

    private var beers: List<BeerAdapterModel>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                LayoutInflater
                        .from(context)
                        .inflate(R.layout.item_list_beer, parent, false)
        )
    }

    override fun getItemCount(): Int {
        var itemCount = 0

        beers?.let { itemCount = it.size }

        return itemCount
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        beers?.let {
            viewHolder.apply {
                populateViews(it[position])
                setOnClickListener(doOnFavoriteBeerSelected, it[position])
            }
        }
    }

    fun updateAdapter(updatedList: List<BeerAdapterModel>) {
        beers?.let {
            val result = DiffUtil.calculateDiff(BeersDiffCallback(it, updatedList))

            this.beers = updatedList.toMutableList()
            result.dispatchUpdatesTo(this)
        }
    }

    fun setData(beers: List<BeerAdapterModel>) {
        this.beers = beers
    }
}
