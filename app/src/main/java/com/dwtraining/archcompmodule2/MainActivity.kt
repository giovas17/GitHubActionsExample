package com.dwtraining.archcompmodule2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dwtraining.network.Connectivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Connectivity.registerNetworkCallback(this)
    }

    override fun onDestroy() {
        Connectivity.unRegisterNetworkCallback()
        super.onDestroy()
    }
}