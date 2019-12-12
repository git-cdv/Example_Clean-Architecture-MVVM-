package com.example.manuel.baseproject.cache.di

import com.google.gson.GsonBuilder
import org.koin.dsl.module

val cacheModule = module {
    factory { GsonBuilder().setPrettyPrinting().create() }
}