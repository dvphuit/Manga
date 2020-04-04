package dvp.manga.ui.custom.transition

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import android.transition.Transition
import android.transition.TransitionValues
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.ImageView
import dvp.manga.R

/**
 * @author dvphu on 30,March,2020
 */

class AVDTransition : Transition {

    private var animatable: AnimatedVectorDrawable

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.AVDTransition)
        val drawable = a.getDrawable(R.styleable.AVDTransition_android_src)
        a.recycle()
        animatable = if (drawable is AnimatedVectorDrawable) {
            drawable
        } else {
            throw IllegalArgumentException("Non-Animatable resource provided.")
        }
    }

    override fun captureStartValues(transitionValues: TransitionValues?) {}

    override fun captureEndValues(transitionValues: TransitionValues?) {}

    override fun createAnimator(
        sceneRoot: ViewGroup?,
        startValues: TransitionValues?,
        endValues: TransitionValues?
    ): Animator? {
        if (endValues?.view !is ImageView) return null
        (endValues.view as ImageView).setImageDrawable(animatable as Drawable?)
        val transition = ValueAnimator.ofInt(0, 1)
        transition.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                animatable.start()
            }
        })
        return transition
    }
}