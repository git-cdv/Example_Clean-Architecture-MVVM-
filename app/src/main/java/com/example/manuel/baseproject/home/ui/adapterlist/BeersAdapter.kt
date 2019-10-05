package com.example.manuel.baseproject.home.ui.adapterlist

import android.content.Context
import android.graphics.drawable.GradientDrawable
import androidx.recyclerview.widget.DiffUtil
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.manuel.baseproject.R
import com.example.manuel.baseproject.home.ui.adapterlist.model.BeerAdapterModel
import kotlinx.android.synthetic.main.item_list_beer.view.*

class BeersAdapter(private val context: Context) : RecyclerView.Adapter<ViewHolder>() {

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
        viewHolder.apply {
            beers?.let {
                val abv = it[position].abv.toString()
                val formattedAbv: String = context.resources.getString(R.string.abv, abv)

                beerAbvTextView.text = formattedAbv
                beerNameTextView.text = it[position].name
                beerTaglineTextView.text = it[position].tagline

                Glide.with(context)
                        .load(it[position].image)
                        .placeholder(R.drawable.ic_close_black)
                        .override(200, 300)
                        .into(beerImageTextView)


                val backgroundColor = ContextCompat.getColor(context, it[position].abvColor)
                (beerAbvTextView.background as GradientDrawable).setColor(backgroundColor)
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

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val beerAbvTextView: AppCompatTextView = view.item_list_beer_abv
    val beerNameTextView: AppCompatTextView = view.item_list_beer_name
    val beerTaglineTextView: AppCompatTextView = view.item_list_beer_tagline
    val beerImageTextView: AppCompatImageView = view.item_list_beer_image
}
