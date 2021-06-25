package com.dwtraining.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build

/**
 * @author Giovani González
 * Created by Giovani on 2020-10-21.
 */
object Connectivity {

    var isConnected = true
    private lateinit var connectivityManager: ConnectivityManager

    private val callback = object: ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            isConnected = true
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            isConnected = false
        }

        override fun onUnavailable() {
            super.onUnavailable()
            isConnected = false
        }
    }

    fun registerNetworkCallback(context: Context) {
        connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(callback)
        } else {
            val request = NetworkRequest.Builder().addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build()
            connectivityManager.registerNetworkCallback(request, callback)
        }
    }

    fun unRegisterNetworkCallback() = connectivityManager.unregisterNetworkCallback(callback)
}