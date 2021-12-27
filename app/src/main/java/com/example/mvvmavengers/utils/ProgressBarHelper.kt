package com.example.mvvmavengers.utils

import android.app.Activity
import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import java.lang.ref.WeakReference

/**
 * Helper class to manage ProgressBar state
 */
object ProgressBarHelper {
    private var progressBar: WeakReference<ProgressBar>? = null

    fun progressBarHandler(context: Context) {

        val layout = (context as Activity).findViewById<View>(android.R.id.content)
            .rootView as ViewGroup

        progressBar = WeakReference(ProgressBar(context, null, android.R.attr.progressBarStyleLarge))
        progressBar?.get()?.isIndeterminate = true

        val params = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT
        )

        val relativeLayout = RelativeLayout(context)

        relativeLayout.gravity = Gravity.CENTER
        relativeLayout.addView(progressBar?.get())

        layout.addView(relativeLayout, params)

        hide()
    }

    fun show() {
        progressBar?.get()?.visibility = View.VISIBLE
    }

    fun hide() {
        progressBar?.get()?.visibility = View.INVISIBLE
    }
}