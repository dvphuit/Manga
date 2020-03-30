package dvp.manga

import android.animation.Animator
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.transition.Transition
import android.transition.TransitionValues
import android.util.AttributeSet
import android.view.ViewGroup


/**
 * @author dvphu on 30,March,2020
 */

class BackgroundTransition : Transition {
    private var startColor: Int = -1
    private var endColor: Int = -1

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        val a = context!!.obtainStyledAttributes(attrs, R.styleable.BackgroundTransition)
        startColor = a.getColor(R.styleable.BackgroundTransition_startColor, -1)
        endColor = a.getColor(R.styleable.BackgroundTransition_endColor, -1)
        a.recycle()
    }

    override fun captureStartValues(transitionValues: TransitionValues) {
    }

    override fun captureEndValues(transitionValues: TransitionValues) {
    }

    override fun createAnimator(sceneRoot: ViewGroup, startValues: TransitionValues?, endValues: TransitionValues?): Animator? {
        if (startValues == null || endValues == null) return null
        val background = endValues.view.background as GradientDrawable
        return ValueAnimator.ofInt(startColor, endColor).apply {
            setEvaluator(ArgbEvaluator())
            addUpdateListener { listener ->
                background.setColor(listener.animatedValue as Int)
            }
        }
    }
}
