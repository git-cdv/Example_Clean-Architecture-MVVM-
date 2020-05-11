package com.example.manuel.baseproject.view.extensions

import android.content.res.Resources
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.example.manuel.baseproject.R

fun AppCompatImageView.loadImage(imageUri: String) {
    val circularProgressDrawable = CircularProgressDrawable(context)
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()

    Glide.with(context)
            .load(imageUri)
            .placeholder(circularProgressDrawable)
            .error(R.drawable.ic_close_black)
            .override(200, 300)
            .into(this)
}

fun View.applyBackgroundColor(color: Int) {
    val backgroundColor = ContextCompat.getColor(context, color)
    (this.background as GradientDrawable).setColor(backgroundColor)
}

fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(this.context).inflate(layoutId, this, attachToRoot)
}

val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()