package dvp.manga.ui.custom.transition

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.drawable.Animatable
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
    private val animatable: Animatable?

    constructor(animatable: Animatable?) : super() {
        require(animatable is Drawable) { "Non-Drawable resource provided." }
        this.animatable = animatable
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.AVDTransition)

        val drawable = a.getDrawable(R.styleable.AVDTransition_android_src)
        a.recycle()
        animatable = if (drawable is Animatable) {
            drawable
        } else {
            throw IllegalArgumentException("Non-Animatable resource provided.")
        }
    }

    override fun captureStartValues(transitionValues: TransitionValues?) { // no-op
    }

    override fun captureEndValues(transitionValues: TransitionValues?) { // no-op
    }

    override fun createAnimator(
        sceneRoot: ViewGroup?,
        startValues: TransitionValues?,
        endValues: TransitionValues?
    ): Animator? {
<<<<<<< HEAD
        if (endValues?.view !is ImageView) return null
        (endValues.view as ImageView).setImageDrawable(animatable as Drawable?)
=======
        if (animatable == null || endValues == null || endValues.view !is ImageView
        ) return null
        val iv = endValues.view as ImageView
        iv.setImageDrawable(animatable as Drawable?)
        // need to return a non-null Animator even though we just want to listen for the start
>>>>>>> parent of 4975aa6... add search view
        val transition = ValueAnimator.ofInt(0, 1)
        transition.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                animatable.start()
            }
        })
        return transition
    }
}