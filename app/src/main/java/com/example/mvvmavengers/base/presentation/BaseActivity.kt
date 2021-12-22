package com.example.mvvmavengers.base.presentation

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_COMPACT
import com.example.mvvmavengers.R
import com.example.mvvmavengers.utils.ProgressBarHelper
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

/**
 * BaseActivity Class
 *
 * Class that holds helper methods for the activity
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ProgressBarHelper.progressBarHandler(this)
    }

    fun showProgress() = ProgressBarHelper.show()

    fun hideProgress() = ProgressBarHelper.hide()

    fun showError(message: String = "Unexpected error") = showAlert(message, "Error", Color.RED)

    fun showError(resId: Int) = showError(getString(resId))

    fun showWarning(message: String) = showAlert(message, "Warning", Color.YELLOW)

    fun showWarning(resId: Int) = showWarning(getString(resId))

    fun showMessage(message: String) = showAlert(message, "Info", Color.GREEN)

    fun showMessage(stringId: Int) = showMessage(getString(stringId))

    private fun showAlert(message: String, title: String, color: Int) {
        val snackBar = Snackbar.make(this.findViewById(android.R.id.content),
            HtmlCompat.fromHtml(title.plus("<br/>").plus(message), FROM_HTML_MODE_COMPACT),
            Snackbar.LENGTH_LONG)

        snackBar.view.apply {
            val params = (layoutParams as FrameLayout.LayoutParams)
            params.gravity = Gravity.TOP
            layoutParams = params
        }

        snackBar.animationMode = BaseTransientBottomBar.ANIMATION_MODE_FADE
        snackBar.setBackgroundTint(color)
        snackBar.anchorView = supportActionBar?.customView
        snackBar.show()
    }
}
