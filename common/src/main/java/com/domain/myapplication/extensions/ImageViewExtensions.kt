package com.domain.myapplication.extensions

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import com.domain.myapplication.helpers.loadImageFromBitmapImage
import com.domain.myapplication.helpers.loadImageFromInternet

fun ImageView.loadImageFromUrl(context: Context, url: String, placeHolderPic: Int) {
    loadImageFromInternet(context, url, this, placeHolderPic)
}

fun ImageView.loadImageFromBitmap(context: Context, bitmam: Bitmap, placeHolderPic: Int) {
    loadImageFromBitmapImage(context, bitmam, this, placeHolderPic)
}
