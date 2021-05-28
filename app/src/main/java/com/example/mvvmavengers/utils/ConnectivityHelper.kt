package com.example.mvvmavengers.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * ConnectivityHelper Class
 *
 * Check device's network connectivity
 */
@KoinApiExtension
object ConnectivityHelper : KoinComponent {

    private val context by inject<Context>()

    /**
     * Check if there is any connectivity
     * @return
     */
    val isOnline: Boolean
        get() {
            return (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).run {
                getNetworkCapabilities(activeNetwork)?.run {
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                            hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                            hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
                } ?: false
            }
        }
}
