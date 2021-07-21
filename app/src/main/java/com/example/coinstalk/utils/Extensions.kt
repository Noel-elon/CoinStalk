package com.example.coinstalk.utils

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadRoundImage(imageUrl: String) {
    Glide.with(this).load(imageUrl).into(this)
}