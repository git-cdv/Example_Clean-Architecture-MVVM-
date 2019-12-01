package com.example.manuel.baseproject.home.favorites.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.manuel.baseproject.R

class FavoritesBeersActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites_beers)

        findViewById<Toolbar>(R.id.favorites_beers_toolbar).apply {
            title = ""
            setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
            setSupportActionBar(this)
        }
    }
}