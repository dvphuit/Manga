package dvp.manga.ui.custom

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

class RatioImageView : AppCompatImageView {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val fourThreeHeight = MeasureSpec.makeMeasureSpec(
            MeasureSpec.getSize(widthMeasureSpec) * 4 / 3,
            MeasureSpec.EXACTLY)
        super.onMeasure(widthMeasureSpec, fourThreeHeight)
    }

}