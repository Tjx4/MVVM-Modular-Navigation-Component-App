package com.domain.myapplication.extensions

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.fragment.app.Fragment

fun Fragment.vibratePhone(duration: Long, effect: Int = VibrationEffect.DEFAULT_AMPLITUDE) {
    val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    if (Build.VERSION.SDK_INT >= 26) {
        vibrator.vibrate(VibrationEffect.createOneShot(duration, effect))
    } else {
        vibrator.vibrate(duration)
    }
}