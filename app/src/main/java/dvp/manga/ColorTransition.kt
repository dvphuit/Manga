package dvp.manga

import android.animation.Animator
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.PorterDuff
import android.graphics.drawable.GradientDrawable
import android.transition.Transition
import android.transition.TransitionValues
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.ImageView


/**
 * @author dvphu on 30,March,2020
 */

class ColorTransition : Transition {
    private var startColor: Int = -1
    private var endColor: Int = -1

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        val a = context!!.obtainStyledAttributes(attrs, R.styleable.ColorTransition)
        startColor = a.getColor(R.styleable.ColorTransition_startColor, -1)
        endColor = a.getColor(R.styleable.ColorTransition_endColor, -1)
        a.recycle()
    }

    override fun captureStartValues(transitionValues: TransitionValues) {
    }

    override fun captureEndValues(transitionValues: TransitionValues) {
    }

    override fun createAnimator(sceneRoot: ViewGroup, startValues: TransitionValues?, endValues: TransitionValues?): Animator? {
        if (startValues == null || endValues == null) return null
        return if (endValues.view is ImageView)
            ValueAnimator.ofInt(endColor, startColor).apply {
                setEvaluator(ArgbEvaluator())
                addUpdateListener { listener ->
                    val value = listener.animatedValue as Int
                    (endValues.view as ImageView).setColorFilter(value, PorterDuff.Mode.SRC_IN)
                }
            }
        else ValueAnimator.ofInt(startColor, endColor).apply {
            setEvaluator(ArgbEvaluator())
            addUpdateListener { listener ->
                val value = listener.animatedValue as Int
                (endValues.view.background as GradientDrawable).setColor(value)
            }
        }
    }
}
