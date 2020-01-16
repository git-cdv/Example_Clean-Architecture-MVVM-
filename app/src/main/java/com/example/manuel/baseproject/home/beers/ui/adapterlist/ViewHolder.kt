package com.example.manuel.baseproject.home.beers.ui.adapterlist

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewPropertyAnimator
import androidx.recyclerview.widget.RecyclerView
import com.example.manuel.baseproject.R
import com.example.manuel.baseproject.view.extensions.applyBackgroundColor
import com.example.manuel.baseproject.view.extensions.loadImage
import com.example.manuel.baseproject.home.beers.ui.adapterlist.model.BeerAdapterModel
import kotlinx.android.synthetic.main.item_list_beer.view.*

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun populateViews(beer: BeerAdapterModel) {
        itemView.item_list_beer_abv.text = getAbv(beer.abv.toString())
        itemView.item_list_beer_name.text = beer.name
        itemView.item_list_beer_tagline.text = beer.tagline
        itemView.item_list_beer_image.loadImage(beer.image)
        itemView.item_list_beer_abv.applyBackgroundColor(beer.abvColor)
        populateFavoriteIconView(beer.isFavorite)
    }

    private fun getAbv(abvId: String) = itemView.context.getString(R.string.abv, abvId)

    fun setOnClickListener(doOnFavoriteBeerSelected: ((BeerAdapterModel) -> Unit), beer: BeerAdapterModel) {
        itemView.item_list_beer_favorite_button.setOnClickListener {
            beer.isFavorite = !beer.isFavorite
            doOnFavoriteBeerSelected.invoke(beer)
            animateFavoriteIcon(beer.isFavorite)
        }
    }

    private fun animateFavoriteIcon(isFavoriteBeer: Boolean) {
        val viewPropertyAnimator = itemView.item_list_beer_favorite_button.animate()

        viewPropertyAnimator
                .scaleX(0.5f)
                .scaleY(0.5f)
                .setDuration(250)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                        populateFavoriteIconView(isFavoriteBeer)
                        restartFavoriteIconSize(viewPropertyAnimator)
                    }

                    override fun onAnimationStart(animation: Animator?) {
                        super.onAnimationStart(animation)
                        itemView.isClickable = false
                    }
                })
    }

    private fun restartFavoriteIconSize(viewPropertyAnimator: ViewPropertyAnimator) {
        viewPropertyAnimator
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(250)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                        viewPropertyAnimator.cancel()
                        itemView.isClickable = true
                    }
                })
    }

    private fun populateFavoriteIconView(isFavorite: Boolean) {
        getFavoriteIcon(isFavorite)?.let {
            itemView.item_list_beer_favorite_button.background = it
        }
    }

    private fun getFavoriteIcon(isFavorite: Boolean): Drawable? {
        return if (isFavorite) {
            itemView.context.getDrawable(R.drawable.ic_star_white_24dp)
        } else {
            itemView.context.getDrawable(R.drawable.ic_star_border_white_24dp)
        }
    }
}
