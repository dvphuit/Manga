package dvp.manga.ui.custom

import android.content.Context
import android.graphics.drawable.AnimatedVectorDrawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import dvp.manga.R


/**
 * @author dvphu on 19,May,2020
 */

class TwoStateAnimButton : AppCompatImageView{

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var drawableOn2Off: Int = -1
    private var drawableOff2On: Int = -1
    private var state: Boolean = false

    val isChecked get() = !state

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.TwoStateAnimButton)
        drawableOn2Off = a.getResourceId(R.styleable.TwoStateAnimButton_srcOn, -1)
        drawableOff2On = a.getResourceId(R.styleable.TwoStateAnimButton_srcOff, -1)
        setImageResource(drawableOff2On)
        a.recycle()
    }

    fun setState(onOff: Boolean) {
        this.state = onOff
        toggle()
    }

    private fun toggle(){
        setImageResource(if (state) drawableOff2On else drawableOn2Off)
        (drawable as AnimatedVectorDrawable).start()
    }
}