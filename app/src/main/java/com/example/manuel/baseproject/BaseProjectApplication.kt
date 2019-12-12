package com.example.manuel.baseproject

import android.app.Application
import com.example.manuel.baseproject.data.di.retrofitModule
import com.example.manuel.baseproject.home.di.beersModule
import com.example.manuel.baseproject.home.di.favoritesModule
import com.example.manuel.baseproject.data.di.beersApiModule
import com.example.manuel.baseproject.data.di.cacheModule
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
                            beersModule,
                            favoritesModule,
                            retrofitModule,
                            beersApiModule,
                            cacheModule
                    )
            )
        }
    }
}