package com.domain.myapplication.extensions

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.domain.myapplication.R
import com.domain.myapplication.constants.ACTIVITY_TRANSITION
import com.domain.myapplication.constants.PAYLOAD_KEY
import com.domain.myapplication.models.Transition

val SLIDE_IN_ACTIVITY = getTransitionAnimation(R.anim.slide_left_and_fade, R.anim.no_transition)
val SLIDE_OUT_ACTIVITY = getTransitionAnimation(R.anim.no_transition, R.anim.slide_right_and_fade)
val FADE_IN_ACTIVITY = getTransitionAnimation(R.anim.fade_in, R.anim.no_transition)
val FADE_OUT_ACTIVITY = getTransitionAnimation(R.anim.no_transition, R.anim.fade_out)
val TRAIL_TO = getTransitionAnimation(R.anim.trail_out, R.anim.trail_in)
val TRAIL_FROM = getTransitionAnimation(R.anim.trail_in2, R.anim.trail_out2)

fun AppCompatActivity.navigateToActivity(
    className: String,
    payload: Bundle?,
    transitionAnimation: Transition
) {
    goToActivity(className, transitionAnimation, payload)
}

private fun AppCompatActivity.goToActivity(
    className: String,
    transitionAnimation: Transition,
    payload: Bundle?
) {
    val intent = Intent(this, Class.forName(className))

    val fullPayload = payload ?: Bundle()
    fullPayload.putIntArray(
        ACTIVITY_TRANSITION, intArrayOf(
            transitionAnimation.inAnimation,
            transitionAnimation.outAnimation
        )
    )

    intent.putExtra(PAYLOAD_KEY, fullPayload)
    startActivity(intent)
}

private fun getTransitionAnimation(inAnimation: Int, outAnimation: Int): Transition {
    val transitionProvider = Transition()
    transitionProvider.inAnimation = inAnimation
    transitionProvider.outAnimation = outAnimation
    return transitionProvider
}