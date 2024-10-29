package com.tuankiet.sample.features.agents.ui

import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.transition.Fade
import androidx.transition.TransitionManager
import com.tuankiet.sample.core.extension.cancelTransition

class AgentDetailsAnimator {

    private val TRANSITION_DELAY = 200L
    private val TRANSITION_DURATION = 400L

    private val TRANSLATE_RIGHT_VALUE = 200f
    private val TRANSLATE_DURATION = 400L

    private val TRANSLATE_LEFT_VALUE = -200f
    private val TRANSLATE_LEFT_DURATION = 400L

    internal fun postponeEnterTransition(activity: FragmentActivity) =
        activity.postponeEnterTransition()

    internal fun cancelTransition(view: View) = view.cancelTransition()

    internal fun translateRightView(view: View) =
        translateView(view, TRANSLATE_RIGHT_VALUE, TRANSLATE_DURATION)

    internal fun translateLeftView(view: View) =
        translateView(view, TRANSLATE_LEFT_VALUE, TRANSLATE_LEFT_DURATION)

    internal fun fadeVisible(viewContainer: ViewGroup, view: View) =
        beginTransitionFor(viewContainer, view, View.VISIBLE)

    internal fun fadeInvisible(viewContainer: ViewGroup, view: View) =
        beginTransitionFor(viewContainer, view, View.INVISIBLE)

    private fun translateView(view: View, translationX: Float, duration: Long) =
        view.animate()
            .translationX(translationX)
            .setDuration(duration)
            .setInterpolator(FastOutSlowInInterpolator())
            .withLayer()
            .setListener(null)
            .start()

    private fun beginTransitionFor(viewContainer: ViewGroup, view: View, visibility: Int) {
        val transition = Fade()
        transition.startDelay = TRANSITION_DELAY
        transition.duration = TRANSITION_DURATION
        TransitionManager.beginDelayedTransition(viewContainer, transition)
        view.visibility = visibility
    }
}
