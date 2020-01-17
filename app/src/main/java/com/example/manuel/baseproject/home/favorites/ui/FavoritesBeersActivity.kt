package com.example.manuel.baseproject.home.favorites.ui

import android.app.Activity
import android.os.Bundle
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
    private val beersAdapter: FavoriteBeersAdapter by inject { parametersOf(favoriteBeerListener) }
    private var favoriteBeerListener: ((FavoriteBeerAdapterModel) -> Unit) = { beerAdapterModel ->
        viewModel.handleRemoveButton(FavoriteBeerAdapterModelToBeerUIMapper.map(beerAdapterModel))
        showSnackBar()
    }

    private var initialBeers: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites_beers)

        bindViews()
        initToolbar()
        initRecyclerView()
        setListeners()
        observerLiveData()
    }

    private fun showSnackBar() {
        val view: ConstraintLayout = findViewById(R.id.favorites_beers_main_container)
        Snackbar.make(view, getString(R.string.activity_favorites_snack_bar_title), Snackbar.LENGTH_SHORT).setAction(getString(R.string.activity_favorites_snack_bar_action)) {
            viewModel.handleUndoButton()
        }.show()
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
        viewModel.isSomeBeerRemovedLiveData.observe(this, Observer(::handleOnBackPressed))
    }

    private fun populateRecyclerView(beersUI: List<BeerUI>?) {
        beersUI?.let {
            if (it.isEmpty()) {
                favorites_beers_recycler_view.visibility = View.GONE
                favorites_beers_empty_view.visibility = View.VISIBLE
            } else {
                favorites_beers_recycler_view.apply {
                    if (initialBeers == null) initialBeers = beersUI.size

                    val beersAdapterModel = BeerUIToFavoriteAdapterModelMapper.map(beersUI)
                    beersAdapter.setData(beersAdapterModel)
                    adapter = beersAdapter
                    setHasFixedSize(true)
                }

                favorites_beers_recycler_view.visibility = View.VISIBLE
                favorites_beers_empty_view.visibility = View.GONE
            }
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
        viewModel.handleOnBackPressed()
    }

    private fun handleOnBackPressed(isSomeBeerRemoved: Boolean) {
        if (!isSomeBeerRemoved) {
            finish()
            return
        }
        setResult(Activity.RESULT_OK)
        finish()
    }
}