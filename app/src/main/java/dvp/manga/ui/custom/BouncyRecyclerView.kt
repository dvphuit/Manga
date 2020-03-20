package dvp.manga.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.view.animation.OvershootInterpolator
import android.widget.OverScroller
import androidx.recyclerview.widget.RecyclerView


/**
 * @author dvphu on 20,March,2020
 */

class BouncyRecyclerView : RecyclerView {
    private val MAX_Y_OVERSCROLL_DISTANCE = 200
    private var mMaxYOverScrollDistance = 0

    constructor(context: Context) : super(context)
    constructor(context: Context, attr: AttributeSet) : super(context, attr)
    constructor(context: Context, attr: AttributeSet, defStyle: Int) : super(context, attr, defStyle)

    init {
        initBounceScrollView()
    }

    private fun initBounceScrollView() {
        val metrics: DisplayMetrics = context.resources.displayMetrics
        val density = metrics.density
        mMaxYOverScrollDistance = (density * MAX_Y_OVERSCROLL_DISTANCE).toInt()

    }

    override fun onOverScrolled(scrollX: Int, scrollY: Int, clampedX: Boolean, clampedY: Boolean) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY)
        Log.d("TEST", "scroll $scrollY - $clampedY ")


        OverScroller(context, OvershootInterpolator())
    }

    override fun overScrollBy(
        deltaX: Int,
        deltaY: Int,
        scrollX: Int,
        scrollY: Int,
        scrollRangeX: Int,
        scrollRangeY: Int,
        maxOverScrollX: Int,
        maxOverScrollY: Int,
        isTouchEvent: Boolean
    ): Boolean {
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, mMaxYOverScrollDistance, isTouchEvent)
    }

}