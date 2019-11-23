package com.example.manuel.baseproject

import android.app.Application
import com.example.manuel.baseproject.home.di.HomeModule
import com.example.manuel.baseproject.di.NetworkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseProjectApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@BaseProjectApplication)
            modules(
                    listOf(
                            HomeModule.beersModule,
                            HomeModule.favoritesModule,
                            NetworkModule.retrofitModule
                    )
            )
        }
    }
}