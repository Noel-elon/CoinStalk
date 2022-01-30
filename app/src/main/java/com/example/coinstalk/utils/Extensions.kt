package com.example.coinstalk.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.bumptech.glide.Glide
import com.example.coinstalk.R

fun ImageView.loadRoundImage(imageUrl: String) {
    Glide.with(this).load(imageUrl).into(this)
}

fun ImageView.loadUrl(url: String) {
    val imageLoader = ImageLoader.Builder(this.context)
        .componentRegistry { add(SvgDecoder(this@loadUrl.context)) }
        .build()

    val request = ImageRequest.Builder(this.context)
        .crossfade(true)
        .crossfade(500)
        .error(R.drawable.ic_coins)
        .placeholder(R.drawable.ic_coins)
        .data(url)
        .target(this)
        .build()

    imageLoader.enqueue(request)
}

fun gained(change : String) : Boolean {
    return !change.startsWith("-", true)
}

fun String.twoDecimals(): String {
    val index: Int = this.indexOf('.')
    return if (index >= 0) {
        substring(0, index + 2)
    } else {
        this
    }
}

fun TextView.setColor(@ColorRes colorId: Int) {
    this.setTextColor(ContextCompat.getColor(this.context, colorId))
}