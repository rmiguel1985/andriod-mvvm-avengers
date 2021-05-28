package com.example.mvvmavengers.initializer

import android.content.Context
import androidx.startup.Initializer
import com.example.mvvmavengers.base.koin.koin.MainModule
import com.example.mvvmavengers.features.avengerslist.koin.avengersListMainModule
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import timber.log.Timber

@InternalCoroutinesApi
@kotlinx.coroutines.ExperimentalCoroutinesApi
class KoinInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        val koinModules: MutableList<Module> = mutableListOf(MainModule)
        koinModules.addAll(avengersListMainModule)

        startKoin {
            androidLogger()
            androidContext(context)
            modules(koinModules)
        }

        Timber.d("Koin is initialized")
    }

    override fun dependencies(): List<Class<out Initializer<*>>> =
        listOf(TimberInitializer::class.java)
}
