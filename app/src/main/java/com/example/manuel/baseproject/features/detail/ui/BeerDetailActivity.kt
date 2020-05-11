package com.example.manuel.baseproject.features.detail.ui

import android.os.Bundle
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import com.example.manuel.baseproject.R
import com.example.manuel.baseproject.features.detail.ui.model.BeerDetailUI
import com.example.manuel.baseproject.view.extensions.dp
import com.example.manuel.baseproject.view.extensions.loadImage
import kotlinx.android.synthetic.main.activity_beers_detail.*


const val BUNDLE_BEER_DETAIL = "BUNDLE_BEER_DETAIL"
const val BUNDLE_TRANSITION_OPTIONS = "BUNDLE_TRANSITION_OPTIONS"

class BeerDetailActivity : AppCompatActivity() {

    companion object {
        private const val ZERO_PX = 0
        private const val EIGHT_PX = 8
        private const val SIXTEEN_PX = 16
    }

    private lateinit var beerDetailUI: BeerDetailUI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beers_detail)

        beerDetailUI = intent!!.extras!!.getSerializable(BUNDLE_BEER_DETAIL) as BeerDetailUI
        populateViews()
        setCloseButtonListener()


        val imageTransitionName: String = getString(R.string.activity_detail_food_pairing_transition_name)
        beers_detail_image_view.transitionName = imageTransitionName
    }

    private fun populateViews() {
        populateProgrammaticallyTextViews()
        beers_detail_image_view.loadImage(beerDetailUI.image)
    }

    private fun populateProgrammaticallyTextViews() {
        val textViews: MutableList<AppCompatTextView> = mutableListOf()
        beerDetailUI.foodPairing.forEachIndexed { _, s ->
            val textView = AppCompatTextView(this).apply {
                text = s
                layoutParams =
                        ViewGroup.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        )
                textSize = EIGHT_PX.toFloat()
                setPadding(ZERO_PX.dp, EIGHT_PX.dp, ZERO_PX, SIXTEEN_PX.dp)
            }
            textViews.add(textView)
        }

        textViews.forEach {
            beers_detail_food_pairing_container.addView(it)
        }
    }

    private fun setCloseButtonListener() {
        beers_detail_close_image_view.setOnClickListener { onBackPressed() }
    }
}