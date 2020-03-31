package dvp.manga.ui.custom

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.widget.LinearLayout


/**
 * @author dvphu on 31,March,2020
 */
class BottomShadowLayout : LinearLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    init {
        val shape = GradientDrawable()
        shape.shape = GradientDrawable.RECTANGLE
        shape.cornerRadius = 100f
        shape.setColor(Color.WHITE)
        background = shape
        outlineProvider = BottomShadowProvider()
    }
}