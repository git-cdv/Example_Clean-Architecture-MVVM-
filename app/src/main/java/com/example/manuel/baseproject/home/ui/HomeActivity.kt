package com.example.manuel.baseproject.home.ui

import androidx.lifecycle.Observer
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.manuel.baseproject.R
import com.example.manuel.baseproject.home.ui.adapterlist.BeersAdapter
import com.example.manuel.baseproject.home.ui.mapper.BeerUIToAdapterModelMapper
import com.example.manuel.baseproject.home.ui.adapterlist.model.BeerAdapterModel
import com.example.manuel.baseproject.home.vm.HomeViewModel
import com.example.manuel.baseproject.home.vm.model.BeerUI
import kotlinx.android.synthetic.main.activity_beers_results.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

private const val KEY_LAST_ITEM_POSITION = "KEY_LAST_ITEM_POSITION"

class HomeActivity : AppCompatActivity() {

    private val viewModel: HomeViewModel by viewModel()

    private val doOnFavoriteBeerSelected: ((BeerAdapterModel) -> Unit)? = {
        Log.i("test", "position $it")
    }

    private val beersAdapter: BeersAdapter by inject { parametersOf(this, doOnFavoriteBeerSelected) }

    private var recyclerView: RecyclerView? = null

    private var savedInstanceState: Bundle? = null

    // TODO Create an ItemDecorator and refactor the CardView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beers_results)

        this.savedInstanceState = savedInstanceState
        bindViews()
        observerLiveData()
    }

    private fun bindViews() {
        recyclerView = findViewById(R.id.recycler_view_beers)
    }

    private fun observerLiveData() {
        viewModel.beers.observe(this, Observer(::onBeersReceived))
        viewModel.isError.observe(this, Observer { onErrorReceived() })
        viewModel.areEmptyBeers.observe(this, Observer { onEmptyBeersReceived() })
        viewModel.isLoading.observe(this, Observer(::onLoadingStateReceived))
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
        recyclerView?.layoutManager = LinearLayoutManager(this)

        beersAdapter.setData(beersAdapterModel)
        recyclerView?.adapter = beersAdapter
        beersAdapter.updateAdapter(beersAdapterModel)

        recyclerView?.setHasFixedSize(true)
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
