package com.example.manuel.baseproject.home.favorites.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.manuel.baseproject.R

class FavoritesBeersActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites_beers)

        bindViews()
        initToolbar()
        setListeners()
    }

    private fun bindViews() {
        toolbar = findViewById(R.id.favorites_beers_toolbar)
    }

    private fun initToolbar() {
        findViewById<Toolbar>(R.id.favorites_beers_toolbar).apply {
            title = ""
            setNavigationIcon(R.drawable.ic_close_white_24dp)
            setSupportActionBar(this)
        }
    }

    private fun setListeners() {
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }
}