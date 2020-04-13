package dvp.manga.ui.custom

import android.content.Context
import android.graphics.Outline
import android.util.AttributeSet
import android.view.View
import android.view.ViewOutlineProvider
import androidx.appcompat.widget.AppCompatImageView
import dvp.manga.utils.dp

class RatioImageView : AppCompatImageView {
    init {
        outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View?, outline: Outline?) {
                outline?.setRoundRect(0, 0, view!!.width, view.height, 5.dp.toFloat())
            }
        }
        clipToOutline = true
        translationZ = 2.dp.toFloat()
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val fourThreeHeight = MeasureSpec.makeMeasureSpec(
            MeasureSpec.getSize(widthMeasureSpec) * 4 / 3,
            MeasureSpec.EXACTLY
        )
        super.onMeasure(widthMeasureSpec, fourThreeHeight)
    }

}