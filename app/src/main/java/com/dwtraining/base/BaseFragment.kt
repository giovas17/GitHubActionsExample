package com.dwtraining.base

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.dwtraining.archcompmodule2.R

/**
 * @author Giovani González
 * Created by Giovani on 2019-06-29.
 */
abstract class BaseFragment : Fragment() {

    private var hasToolbar: Boolean = false
    private var toolbarResId: Int = R.layout.toolbar
    private var toolbarId: Int = R.id.toolbar
    private lateinit var parentView: View
    private val parentActivity: AppCompatActivity? by lazy { (activity as AppCompatActivity) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hasToolbar = arguments?.getBoolean(HAS_TOOLBAR_KEY, false) ?: false
        toolbarResId = arguments?.getInt(RES_ID_CUSTOM_TOOLBAR, toolbarResId) ?: toolbarResId
        toolbarId = arguments?.getInt(ID_CUSTOM_TOOLBAR, toolbarId) ?: toolbarId
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        parentView = inflater.inflate(R.layout.base_fragment, container, false)
        val fragmentContainer = parentView.findViewById<FrameLayout>(R.id.container_view)
        fragmentContainer?.let {
            val contentView = setContentView()
            it.addView(contentView)
            if (hasToolbar) {
                addToolbar()
            }
        }
        return parentView
    }

    abstract fun setContentView(container: ViewGroup? = null) : View
    abstract fun onViewFragmentCreated(view: View, savedInstanceState: Bundle?)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showSystemUI()
        onViewFragmentCreated(view, savedInstanceState)
    }

    private fun addToolbar(resIdToolbar: Int = toolbarResId, idToolbar: Int = toolbarId) {
        val toolbarView = LayoutInflater.from(context).inflate(resIdToolbar, null, false)
        val toolbar = toolbarView.findViewById<Toolbar>(idToolbar)
        toolbar?.let {
            (parentView as LinearLayout).addView(toolbarView, 0)
            parentActivity?.setSupportActionBar(it)
            setHasOptionsMenu(true)
            if (!hasToolbar) { hasToolbar = true }
        }
    }

    fun setTitle(title: String) {
        if (hasToolbar) {
            parentActivity?.supportActionBar?.title = title
        }
    }

    fun showBackIconOnToolbar() {
        if (hasToolbar) {
            parentActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    @Suppress("DEPRECATION")
    private fun showSystemUI() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            activity?.window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
        } else {
            activity?.window?.setDecorFitsSystemWindows(true)
            activity?.window?.insetsController?.apply {
                show(WindowInsets.Type.navigationBars())
                show(WindowInsets.Type.statusBars())
            }
        }
    }

    @Suppress("DEPRECATION")
    fun fullScreen() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            activity?.window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                    // Set the content to appear under the system bars so that the
                    // content doesn't resize when the system bars hide and show.
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    // Hide the nav bar and status bar
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN)
        } else {
            activity?.window?.setDecorFitsSystemWindows(false)
            activity?.window?.insetsController?.apply {
                hide(WindowInsets.Type.navigationBars())
                hide(WindowInsets.Type.statusBars())
            }
        }
    }

    fun onBackPressed(listener:() -> Unit) {
        requireActivity().onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                listener()
            }
        })
    }

    fun onBackPressed() = requireActivity().onBackPressed()

    companion object {
        const val HAS_TOOLBAR_KEY = "_hasToolbar"
        const val RES_ID_CUSTOM_TOOLBAR = "customToolbarResId"
        const val ID_CUSTOM_TOOLBAR = "customToolbarId"
    }
}