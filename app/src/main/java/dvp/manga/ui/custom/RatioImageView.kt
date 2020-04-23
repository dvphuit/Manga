package dvp.manga.ui.custom

import android.content.Context
import android.graphics.Color
import android.graphics.Outline
import android.util.AttributeSet
import android.view.View
import android.view.ViewOutlineProvider
import androidx.appcompat.widget.AppCompatImageView
import dvp.manga.R
import dvp.manga.utils.dp

class RatioImageView : AppCompatImageView {
    private var rWidth = 3
    private var rHeight = 4

    init {
        outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View?, outline: Outline?) {
                outline?.setRoundRect(0, 0, view!!.width, view.height, 5.dp.toFloat())
            }
        }
        clipToOutline = true
        setBackgroundColor(Color.WHITE)
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.RatioImageView)
        rWidth = a.getInteger(R.styleable.RatioImageView_widthRatio, rWidth)
        rHeight = a.getInteger(R.styleable.RatioImageView_heightRatio, rHeight)
        a.recycle()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val fourThreeHeight = MeasureSpec.makeMeasureSpec(
            MeasureSpec.getSize(widthMeasureSpec) * rHeight / rWidth,
            MeasureSpec.EXACTLY
        )
        super.onMeasure(widthMeasureSpec, fourThreeHeight)
    }

}