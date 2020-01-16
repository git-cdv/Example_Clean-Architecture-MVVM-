package com.example.manuel.baseproject.home.detail

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import com.example.manuel.baseproject.R
import com.example.manuel.baseproject.home.detail.model.BeerDetailUI
import com.example.manuel.baseproject.view.extensions.loadImage
import kotlinx.android.synthetic.main.activity_beers_detail.*

const val BUNDLE_BEER_DETAIL = "BUNDLE_BEER_DETAIL"

class BeerDetailActivity : AppCompatActivity() {

    private lateinit var beerDetailUI: BeerDetailUI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beers_detail)

        beerDetailUI = intent!!.extras!!.getSerializable(BUNDLE_BEER_DETAIL) as BeerDetailUI

        Log.i("test", "${beerDetailUI.foodPairing}")

        val textViews: MutableList<AppCompatTextView> = mutableListOf()
        beerDetailUI.foodPairing.forEachIndexed { _, s ->
            val textView = AppCompatTextView(this).apply {
                text = s
                layoutParams =
                        ViewGroup.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        )
                textSize = 16f
            }
            textViews.add(textView)
        }

        textViews.forEach {
            beers_detail_food_pairing_container.addView(it)
        }

        beers_detail_image_view.loadImage(beerDetailUI.image)
    }
}