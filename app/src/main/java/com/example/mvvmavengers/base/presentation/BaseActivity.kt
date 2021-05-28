package com.example.mvvmavengers.base.presentation

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mvvmavengers.utils.ProgressBarHelper
import com.tapadoo.alerter.Alerter

const val ALERT_DURATION: Long = 2000

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
        Alerter.create(this)
                .setTitle(title)
                .setText(message)
                .hideIcon()
                .setDuration(ALERT_DURATION)
                .setBackgroundColorInt(color)
                .show()
    }
}
