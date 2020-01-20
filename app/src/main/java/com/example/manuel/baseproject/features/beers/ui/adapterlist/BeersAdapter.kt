package com.example.manuel.baseproject.features.beers.ui.adapterlist

import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.manuel.baseproject.R
import com.example.manuel.baseproject.features.beers.ui.adapterlist.model.BeerAdapterModel
import com.example.manuel.baseproject.view.extensions.inflate

class BeersAdapter(
        private val favoriteBeerListener: (BeerAdapterModel) -> Unit,
        private val beerDetailListener: (BeerAdapterModel, beerImageView: AppCompatImageView) -> Unit
) : RecyclerView.Adapter<ViewHolder>() {

    private lateinit var beers: List<BeerAdapterModel>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.item_list_beer)
        val viewHolder = ViewHolder(view)

        setFavoriteBeerListener(viewHolder)
        setBeerItemListener(viewHolder)
        avoidMultipleClicks(view)

        return viewHolder
    }

    private fun setBeerItemListener(viewHolder: ViewHolder) {
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                beerDetailListener.invoke(beers[position], viewHolder.beerImageView)
            }
        }
    }

    private fun setFavoriteBeerListener(viewHolder: ViewHolder) {
        viewHolder.favoriteImageView.setOnClickListener {
            val position = viewHolder.adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val beer = beers[position].apply {
                    isFavorite = !isFavorite
                }
                viewHolder.animateFavoriteIcon(beer.isFavorite)
                favoriteBeerListener.invoke(beer)
            }
        }
    }

    private fun avoidMultipleClicks(view: View) {
        view.isClickable = false
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            view.isClickable = true
        }, 1000)
    }

    override fun getItemCount(): Int = beers.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            populateViews(beers[position])
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
