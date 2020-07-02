package dvp.manga.ui.custom

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.AnimatedVectorDrawable
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatImageView
import dvp.manga.R


/**
 * @author dvphu on 19,May,2020
 */

class TwoStateAnimButton : AppCompatImageView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var drawableOn: Int = -1
    private var drawableOff: Int = -1
    private var state: Boolean = false


    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.TwoStateAnimButton)
        drawableOn = a.getResourceId(R.styleable.TwoStateAnimButton_srcOn, -1)
        drawableOff = a.getResourceId(R.styleable.TwoStateAnimButton_srcOff, -1)
        setImageResource(drawableOff)
        a.recycle()
    }

    fun setState(onOff: Boolean) {
        this.state = onOff
        toggle()
    }

    private fun toggle(){
        val drawable = if (state) drawableOn else drawableOff
        setImageResource(drawable)
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_UP -> {
                toggle()
                (drawable as AnimatedVectorDrawable).start()
                performClick()
                this.state = !this.state
            }
            MotionEvent.ACTION_CANCEL-> return false
        }
        return true
    }
}