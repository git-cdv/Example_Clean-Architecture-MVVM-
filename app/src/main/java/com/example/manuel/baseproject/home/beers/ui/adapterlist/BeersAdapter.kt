package com.example.manuel.baseproject.home.beers.ui.adapterlist

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.manuel.baseproject.R
import com.example.manuel.baseproject.view.extensions.inflate
import com.example.manuel.baseproject.home.beers.ui.adapterlist.model.BeerAdapterModel

class BeersAdapter(
        private val favoriteBeerListener: (BeerAdapterModel) -> Unit,
        private val beerDetailListener: (BeerAdapterModel, beerImageView: AppCompatImageView) -> Unit
) : RecyclerView.Adapter<ViewHolder>() {

    private lateinit var beers: List<BeerAdapterModel>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.item_list_beer)
        val viewHolder = ViewHolder(view)
        setBeerItemListener(view, viewHolder)

        return viewHolder
    }

    private fun setBeerItemListener(view: View, viewHolder: ViewHolder) {
        view.setOnClickListener {
            val position = viewHolder.adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                beerDetailListener.invoke(beers[position], viewHolder.beerImageView)
            }
        }
    }

    override fun getItemCount(): Int = beers.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            populateViews(beers[position])
            setOnClickListener(favoriteBeerListener, beers[position])
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
