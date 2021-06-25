package com.dwtraining.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.dwtraining.archcompmodule2.R

class SimpleProgressDialog : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_progress_sharingan, container, false)
        val imageView = view.findViewById<ImageView>(R.id.imageProgress)
        Glide.with(this).asGif().load(R.raw.sharingan).into(imageView)
        return view
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setCanceledOnTouchOutside(false)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    fun setMessage(message: String) {
        val label = dialog!!.findViewById<TextView>(R.id.textMessage)
        label.text = message
    }
}