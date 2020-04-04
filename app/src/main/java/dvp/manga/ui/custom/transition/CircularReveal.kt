package dvp.manga.ui.custom.transition

import android.animation.Animator
import android.content.Context
import android.graphics.Point
import android.transition.TransitionValues
import android.transition.Visibility
import android.util.AttributeSet
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import androidx.annotation.IdRes
import dvp.manga.R
import dvp.manga.utils.NoPauseAnimator
import kotlin.math.hypot
import kotlin.math.max
import kotlin.math.roundToInt

/**
 * @author dvphu on 27,March,2020
 */

class CircularReveal : Visibility {
    private var center: Point? = null
    private var startRadius = 0f
    private var endRadius = 0f
    @IdRes
    private var centerOnId = View.NO_ID
    private var centerOn: View? = null

    constructor() : super()

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.CircularReveal)
        startRadius = a.getDimension(R.styleable.CircularReveal_startRadius, 0f)
        endRadius = a.getDimension(R.styleable.CircularReveal_endRadius, 0f)
        centerOnId = a.getResourceId(R.styleable.CircularReveal_centerOn, View.NO_ID)
        a.recycle()
    }

    /**
     * The center point of the reveal or conceal, relative to the target `view`.
     */
    fun setCenter(center: Point) {
        this.center = center
    }

    override fun onAppear(
        sceneRoot: ViewGroup, view: View?,
        startValues: TransitionValues?,
        endValues: TransitionValues?
    ): Animator? {
        if (view == null || view.height == 0 || view.width == 0) return null
        ensureCenterPoint(sceneRoot, view)
        return NoPauseAnimator(ViewAnimationUtils.createCircularReveal(
            view,
//            center!!.x,
//            center!!.y,
            1080,
            0,
            startRadius,
            getFullyRevealedRadius(view)
        ))
    }

    override fun onDisappear(
        sceneRoot: ViewGroup, view: View?,
        startValues: TransitionValues?,
        endValues: TransitionValues?
    ): Animator? {
        if (view == null || view.height == 0 || view.width == 0) return null
        ensureCenterPoint(sceneRoot, view)
        return NoPauseAnimator(ViewAnimationUtils.createCircularReveal(
            view,
//            center!!.x,
////            center!!.y,
            1080,
            0,
            getFullyRevealedRadius(view),
            endRadius
        ))
    }

    private fun ensureCenterPoint(sceneRoot: ViewGroup, view: View) {
        if (center != null) return
        if (centerOn != null || centerOnId != View.NO_ID) {
            val source = if (centerOn != null) {
                centerOn
            } else {
                sceneRoot.findViewById(centerOnId)
            }
            if (source != null) { // use window location to allow views in diff hierarchies
                val loc = IntArray(2)
                source.getLocationInWindow(loc)
                val srcX = loc[0] + source.width / 2
                val srcY = loc[1] + source.height / 2
                view.getLocationInWindow(loc)
                center = Point(srcX - loc[0], srcY - loc[1])
            }
        }
        // else use the pivot point
        if (center == null) {
            center = Point(view.pivotX.roundToInt(), view.pivotY.roundToInt())
        }
    }

    private fun getFullyRevealedRadius(view: View): Float {
        return hypot(
            max(center!!.x, view.width - center!!.x).toDouble(),
            max(center!!.y, view.height - center!!.y).toDouble()
        ).toFloat()
    }
}