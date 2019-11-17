package com.example.manuel.baseproject.home.commons.extensions

import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.manuel.baseproject.R

fun AppCompatImageView.loadImage(imageUri: String) {
    Glide.with(context)
            .load(imageUri)
            .placeholder(R.drawable.ic_close_black)
            .override(200, 300)
            .into(this)
}

fun View.applyBackgroundColor(color: Int) {
    val backgroundColor = ContextCompat.getColor(context, color)
    (this.background as GradientDrawable).setColor(backgroundColor)
}