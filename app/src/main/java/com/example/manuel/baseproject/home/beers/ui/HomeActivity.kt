package com.example.manuel.baseproject.home.beers.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.manuel.baseproject.R
import com.example.manuel.baseproject.home.beers.ui.adapterlist.BeersAdapter
import com.example.manuel.baseproject.home.beers.ui.adapterlist.model.BeerAdapterModel
import com.example.manuel.baseproject.home.beers.ui.mapper.BeerAdapterModelToBeerUIMapper
import com.example.manuel.baseproject.home.beers.ui.mapper.BeerUIToAdapterModelMapper
import com.example.manuel.baseproject.home.beers.vm.HomeViewModel
import com.example.manuel.baseproject.home.beers.vm.model.BeerUI
import com.example.manuel.baseproject.home.favorites.ui.FavoritesBeersActivity
import kotlinx.android.synthetic.main.activity_beers_results.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


private const val KEY_LAST_ITEM_POSITION = "KEY_LAST_ITEM_POSITION"

class HomeActivity : AppCompatActivity() {

    private val viewModel: HomeViewModel by viewModel()
    private var doOnFavoriteBeerSelected: ((BeerAdapterModel) -> Unit)? = null
    private val beersAdapter: BeersAdapter by inject { parametersOf(doOnFavoriteBeerSelected) }
    private var recyclerView: RecyclerView? = null
    private var savedInstanceState: Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beers_results)

        this.savedInstanceState = savedInstanceState
        initDoOnFavoriteBeerSelected()
        bindViews()
        setListeners()
        observerLiveData()
    }

    private fun initDoOnFavoriteBeerSelected() {
        doOnFavoriteBeerSelected = {
            viewModel.handleFavoriteButton(BeerAdapterModelToBeerUIMapper.map(it))
        }
    }

    private fun bindViews() {
        recyclerView = findViewById(R.id.recycler_view_beers)
    }

    private fun setListeners() {
        favorite_floating_button.setOnClickListener { intentToFavoritesBeersActivity() }
    }

    private fun intentToFavoritesBeersActivity() {
        Intent(this, FavoritesBeersActivity::class.java)
                .run {
                    startActivity(this)
                    overridePendingTransition(R.anim.slide_up, R.anim.slide_down)
                }
    }

    private fun observerLiveData() {
        viewModel.beersLiveData.observe(this, Observer(::onBeersReceived))
        viewModel.isErrorLiveData.observe(this, Observer { onErrorReceived() })
        viewModel.areEmptyBeersLiveData.observe(this, Observer { onEmptyBeersReceived() })
        viewModel.isLoadingLiveData.observe(this, Observer(::onLoadingStateReceived))
    }

    private fun onBeersReceived(beers: List<BeerUI>) {
        showBeers(beers)
    }

    private fun showBeers(beersUI: List<BeerUI>?) {
        beersUI?.let {
            populateRecyclerView(BeerUIToAdapterModelMapper.map(it))
            restorePreviousUIState()
            animateRecyclerViewOnlyInTheBeginning()
        }
    }

    private fun populateRecyclerView(beersAdapterModel: List<BeerAdapterModel>) {
        recyclerView?.apply {
            layoutManager = LinearLayoutManager(this@HomeActivity)
            beersAdapter.setData(beersAdapterModel)
            adapter = beersAdapter.apply { updateAdapter(beersAdapterModel) }
            setHasFixedSize(true)
        }
    }

    /**
     *  If is necessary, this method recover the UI state when Android kill the activity,
     *  so the UI will be restored.
     *
     *  The first time there is not data in the adapter and maybe the device has a network error,
     *  also prevents it.
     * */
    private fun restorePreviousUIState() {
        savedInstanceState?.let {
            val lastItemPosition = it.getInt(KEY_LAST_ITEM_POSITION)
            recyclerView?.scrollToPosition(lastItemPosition)
        }
    }

    private fun animateRecyclerViewOnlyInTheBeginning() {
        if (getLastItemPosition() == 0) {
            recyclerView?.layoutAnimation =
                    AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_fall_down)
        }
    }

    private fun getLastItemPosition(): Int {
        var lastItemPosition = 0

        savedInstanceState?.let {
            if (it.containsKey(KEY_LAST_ITEM_POSITION)) {
                lastItemPosition = it.getInt(KEY_LAST_ITEM_POSITION)
            }
        }

        return lastItemPosition
    }

    private fun onErrorReceived() {
        AlertDialog.Builder(this)
                .setTitle(R.string.network_connection_error_title)
                .setCancelable(false)
                .setNegativeButton(R.string.network_connection_error_cancel) { _, _ -> finish() }
                .setPositiveButton(R.string.network_connection_error_action) { _, _ -> viewModel.handleBeersLoad() }
                .show()
    }

    private fun onEmptyBeersReceived() {
        // do something
    }

    private fun onLoadingStateReceived(isLoading: Boolean) {
        showSpinner(isLoading)
    }

    private fun showSpinner(isLoading: Boolean) {
        main_activity_spinner.apply {
            visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        saveRecyclerViewState(outState)
    }

    private fun saveRecyclerViewState(outState: Bundle) {
        recyclerView?.let {
            it.layoutManager?.let { layoutManager ->
                val lastVisibleItemPosition =
                        (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

                outState.putInt(KEY_LAST_ITEM_POSITION, lastVisibleItemPosition)
            }
        }
    }
}
