package com.domain.myapplication.extensions

import android.content.Context
import android.widget.ImageView
import com.domain.myapplication.helpers.loadImageFromInternet

fun ImageView.loadImageFromUrl(context: Context, url: String, placeHolderPic: Int) {
    loadImageFromInternet(context, url, this, placeHolderPic)
}

