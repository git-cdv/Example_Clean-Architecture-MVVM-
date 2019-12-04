package com.example.manuel.baseproject.home.favorites.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.manuel.baseproject.R
import com.example.manuel.baseproject.home.beers.ui.mapper.BeerUIToFavoriteAdapterModelMapper
import com.example.manuel.baseproject.home.beers.ui.mapper.FavoriteBeerAdapterModelToBeerUIMapper
import com.example.manuel.baseproject.home.beers.vm.model.BeerUI
import com.example.manuel.baseproject.home.favorites.ui.adapterlist.FavoriteBeersAdapter
import com.example.manuel.baseproject.home.favorites.ui.adapterlist.model.FavoriteBeerAdapterModel
import com.example.manuel.baseproject.home.favorites.vm.FavoritesBeersViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_favorites_beers.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class FavoritesBeersActivity : AppCompatActivity() {

    private val viewModel: FavoritesBeersViewModel by viewModel()
    private lateinit var toolbar: Toolbar
    private val beersAdapter: FavoriteBeersAdapter by inject { parametersOf(doOnFavoriteBeerSelected) }
    private var doOnFavoriteBeerSelected: ((FavoriteBeerAdapterModel) -> Unit)? = null
    private var initialBeers: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites_beers)

        initDoOnFavoriteBeerSelectedVar()
        bindViews()
        initToolbar()
        initRecyclerView()
        setListeners()
        observerLiveData()
    }

    private fun initDoOnFavoriteBeerSelectedVar() {
        doOnFavoriteBeerSelected = { beerAdapterModel ->
            Log.i("test", "Remove beer id = ${beerAdapterModel.id}")
            viewModel.handleRemoveButton(FavoriteBeerAdapterModelToBeerUIMapper.map(beerAdapterModel))
            showSnackBar()
        }
    }

    private fun showSnackBar() {
        val view: ConstraintLayout = findViewById(R.id.favorites_beers_main_container)
        Snackbar
                .make(view, getString(R.string.activity_favorites_snackbar_title), Snackbar.LENGTH_SHORT)
                .setAction(getString(R.string.activity_favorites_snackbar_action)) {
                    viewModel.handleUndoButton()
                }
                .addCallback(object : Snackbar.Callback() {
                    override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                        super.onDismissed(transientBottomBar, event)
                        viewModel.handleOnSnackBarHidden()
                    }
                })
                .show()
    }

    private fun bindViews() {
        toolbar = findViewById(R.id.favorites_beers_toolbar)
    }

    private fun initToolbar() {
        findViewById<Toolbar>(R.id.favorites_beers_toolbar).apply {
            title = getString(R.string.activity_favorites_toolbar_title)
            setTitleTextColor(
                    ContextCompat.getColor(
                            this@FavoritesBeersActivity,
                            R.color.white
                    )
            )
            setNavigationIcon(R.drawable.ic_close_white_24dp)
            setSupportActionBar(this)
        }
    }

    private fun initRecyclerView() {
        favorites_beers_recycler_view.apply {
            layoutManager = LinearLayoutManager(this@FavoritesBeersActivity)
            setHasFixedSize(true)
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
            if (initialBeers == null) initialBeers = beersUI.size

            val beersAdapterModel = BeerUIToFavoriteAdapterModelMapper.map(beersUI)
            beersAdapter.setData(beersAdapterModel)
            adapter = beersAdapter
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

    override fun onBackPressed() {
        val finalBeers = beersAdapter.itemCount
        initialBeers?.let {
            Log.i("test", "Refresh previous activity = ${initialBeers != 0 && finalBeers < it}")
            Log.i("test", "final beers = ${finalBeers}")
            Log.i("test", "initial beers = ${initialBeers}")
        }

        super.onBackPressed()
    }
}