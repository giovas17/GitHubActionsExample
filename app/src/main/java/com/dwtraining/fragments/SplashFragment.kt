package com.dwtraining.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.dwtraining.archcompmodule2.R
import com.dwtraining.base.BaseFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @author Giovani González
 * Created by Giovani on 2020-06-29.
 */
class SplashFragment : BaseFragment() {

    override fun setContentView(container: ViewGroup?): View {
        return LayoutInflater.from(context).inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewFragmentCreated(view: View, savedInstanceState: Bundle?) {
        fullScreen()
        lifecycleScope.launch { waitAndChangeScreen() }
    }

    private suspend fun waitAndChangeScreen() {
        delay(2500)
        findNavController().navigate(R.id.loginFragment)
    }
}