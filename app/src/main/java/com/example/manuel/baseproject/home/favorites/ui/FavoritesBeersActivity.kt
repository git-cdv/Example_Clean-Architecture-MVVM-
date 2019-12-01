package com.example.manuel.baseproject.home.favorites.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.manuel.baseproject.R
import com.example.manuel.baseproject.home.beers.ui.adapterlist.BeersAdapter
import com.example.manuel.baseproject.home.beers.ui.mapper.BeerUIToAdapterModelMapper
import com.example.manuel.baseproject.home.beers.vm.model.BeerUI
import com.example.manuel.baseproject.home.favorites.vm.FavoritesBeersViewModel
import kotlinx.android.synthetic.main.activity_favorites_beers.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesBeersActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private val viewModel: FavoritesBeersViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites_beers)

        bindViews()
        initToolbar()
        setListeners()
        observerLiveData()
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

    private fun observerLiveData() {
        viewModel.beersLiveData.observe(this, Observer(::populateRecyclerView))
        viewModel.isLoadingLiveData.observe(this, Observer(::onLoadingStateReceived))
    }

    private fun populateRecyclerView(beersUI: List<BeerUI>) {
        favorites_beers_recycler_view.apply {
            layoutManager = LinearLayoutManager(this@FavoritesBeersActivity)
            val beersAdapter = BeersAdapter()
            val beersAdapterModel = BeerUIToAdapterModelMapper.map(beersUI)
            beersAdapter.setData(beersAdapterModel)
            adapter = beersAdapter.apply { updateAdapter(beersAdapterModel) }
            setHasFixedSize(true)
        }
    }

    private fun onLoadingStateReceived(isLoading: Boolean) {
        showSpinner(isLoading)
    }

    private fun showSpinner(isLoading: Boolean) {
        favorites_beers_spinner.apply {
            visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }
}