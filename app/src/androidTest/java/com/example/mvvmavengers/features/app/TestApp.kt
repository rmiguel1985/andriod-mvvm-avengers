package com.example.mvvmavengers.features.app

import android.app.Application

import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

class TestApp : Application() {
    override fun onCreate() {
        super.onCreate()
        stopKoin()
        startKoin {
            androidLogger()
            androidContext(this@TestApp)
            modules(emptyList())
        }
    }
}
