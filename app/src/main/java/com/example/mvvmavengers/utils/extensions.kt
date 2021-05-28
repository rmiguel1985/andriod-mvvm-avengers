package com.example.mvvmavengers.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import com.bumptech.glide.Glide

const val IMAGE_SIZE = 200

fun ViewGroup.inflate(@LayoutRes layoutRes: Int): View =
    LayoutInflater.from(context)
        .inflate(layoutRes, this, false)

fun ImageView.loadUrl(url: String) =
    Glide.with(this).load(url).into(this)

fun ImageView.loadUrlWithCircleCrop(url: String) =
    Glide.with(this)
        .load(url)
        .circleCrop()
        .override(IMAGE_SIZE, IMAGE_SIZE).into(this)
