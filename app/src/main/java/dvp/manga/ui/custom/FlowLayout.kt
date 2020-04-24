package dvp.manga.ui.custom

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import android.widget.TextView
import dvp.manga.R
import dvp.manga.utils.dp
import kotlin.math.max


class FlowLayout(context: Context, attrs: AttributeSet?, defStyle: Int) : ViewGroup(context, attrs, defStyle), View.OnClickListener {
    private var mGravity = Gravity.START or Gravity.TOP
    private val mLines: MutableList<MutableList<View>> = mutableListOf()
    private val mLineHeights: MutableList<Int> = mutableListOf()
    private val mLineMargins: MutableList<Int> = mutableListOf()

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    override fun onClick(v: View?) {

    }

    private fun buildLabel(text: String, tag: Int = 0): TextView {
        return TextView(context).apply {
            layoutParams = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply { setMargins(0.dp, 4.dp, 8.dp, 4.dp) }
            this.text = text
            this.setTextColor(Color.WHITE)
            this.typeface = Typeface.DEFAULT_BOLD
            this.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11f)
            setPadding(8.dp, 4.dp, 8.dp, 4.dp)
            setBackgroundResource(R.drawable.bg_tag_selector)
            setOnClickListener(this@FlowLayout)
        }
    }

    fun <T> setList(list: List<T>?) {
        this.removeAllViews()
        list?.forEachIndexed { index, item ->
            addView(buildLabel(item.toString(), index))
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val sizeWidth = MeasureSpec.getSize(widthMeasureSpec) - paddingLeft - paddingRight
        val sizeHeight = MeasureSpec.getSize(heightMeasureSpec)
        val modeWidth = MeasureSpec.getMode(widthMeasureSpec)
        val modeHeight = MeasureSpec.getMode(heightMeasureSpec)
        var width = 0
        var height = paddingTop + paddingBottom
        var lineWidth = 0
        var lineHeight = 0
        val childCount = childCount
        for (i in 0 until childCount) {
            val child: View = getChildAt(i)
            val lastChild = i == childCount - 1
            if (child.visibility == View.GONE) {
                if (lastChild) {
                    width = max(width, lineWidth)
                    height += lineHeight
                }
                continue
            }
            measureChildWithMargins(child, widthMeasureSpec, lineWidth, heightMeasureSpec, height)
            val lp = child.layoutParams as MarginLayoutParams
            var childWidthMode = MeasureSpec.AT_MOST
            var childWidthSize = sizeWidth
            var childHeightMode = MeasureSpec.AT_MOST
            var childHeightSize = sizeHeight
            if (lp.width == LayoutParams.MATCH_PARENT) {
                childWidthMode = MeasureSpec.EXACTLY
                childWidthSize -= lp.leftMargin + lp.rightMargin
            } else if (lp.width >= 0) {
                childWidthMode = MeasureSpec.EXACTLY
                childWidthSize = lp.width
            }
            if (lp.height >= 0) {
                childHeightMode = MeasureSpec.EXACTLY
                childHeightSize = lp.height
            } else if (modeHeight == MeasureSpec.UNSPECIFIED) {
                childHeightMode = MeasureSpec.UNSPECIFIED
                childHeightSize = 0
            }
            child.measure(
                MeasureSpec.makeMeasureSpec(childWidthSize, childWidthMode),
                MeasureSpec.makeMeasureSpec(childHeightSize, childHeightMode)
            )
            val childWidth: Int = child.measuredWidth + lp.leftMargin + lp.rightMargin
            if (lineWidth + childWidth > sizeWidth) {
                width = max(width, lineWidth)
                lineWidth = childWidth
                height += lineHeight
                lineHeight = child.measuredHeight + lp.topMargin + lp.bottomMargin
            } else {
                lineWidth += childWidth
                lineHeight = max(lineHeight, child.measuredHeight + lp.topMargin + lp.bottomMargin)
            }
            if (lastChild) {
                width = max(width, lineWidth)
                height += lineHeight
            }
        }
        width += paddingLeft + paddingRight
        setMeasuredDimension(
            if (modeWidth == MeasureSpec.EXACTLY) sizeWidth else width,
            if (modeHeight == MeasureSpec.EXACTLY) sizeHeight else height
        )
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        mLines.clear()
        mLineHeights.clear()
        mLineMargins.clear()
        val width = width
        val height = height
        var linesSum = paddingTop
        var lineWidth = 0
        var lineHeight = 0
        var lineViews: MutableList<View> = mutableListOf()
        val horizontalGravityFactor: Float = when (mGravity and Gravity.HORIZONTAL_GRAVITY_MASK) {
            Gravity.START -> 0f
            Gravity.CENTER_HORIZONTAL -> .5f
            Gravity.END -> 1f
            else -> 0f
        }
        for (i in 0 until childCount) {
            val child: View = getChildAt(i)
            if (child.visibility == View.GONE) {
                continue
            }
            val lp = child.layoutParams as MarginLayoutParams
            val childWidth: Int = child.measuredWidth + lp.leftMargin + lp.rightMargin
            val childHeight: Int = child.measuredHeight + lp.bottomMargin + lp.topMargin
            if (lineWidth + childWidth > width) {
                mLineHeights.add(lineHeight)
                mLines.add(lineViews)
                mLineMargins.add(((width - lineWidth) * horizontalGravityFactor).toInt() + paddingLeft)
                linesSum += lineHeight
                lineHeight = 0
                lineWidth = 0
                lineViews = ArrayList()
            }
            lineWidth += childWidth
            lineHeight = max(lineHeight, childHeight)
            lineViews.add(child)
        }
        mLineHeights.add(lineHeight)
        mLines.add(lineViews)
        mLineMargins.add(((width - lineWidth) * horizontalGravityFactor).toInt() + paddingLeft)
        linesSum += lineHeight
        var verticalGravityMargin = 0
        when (mGravity and Gravity.VERTICAL_GRAVITY_MASK) {
            Gravity.TOP -> {
            }
            Gravity.CENTER_VERTICAL -> verticalGravityMargin = (height - linesSum) / 2
            Gravity.BOTTOM -> verticalGravityMargin = height - linesSum
            else -> {
            }
        }
        val numLines = mLines.size
        var left: Int
        var top = paddingTop
        for (i in 0 until numLines) {
            lineHeight = mLineHeights[i]
            lineViews = mLines[i]
            left = mLineMargins[i]
            val children = lineViews.size
            for (j in 0 until children) {
                val child: View = lineViews[j]
                if (child.visibility == View.GONE) {
                    continue
                }
                val lp = child.layoutParams as MarginLayoutParams
                if (lp.height == LayoutParams.MATCH_PARENT) {
                    var childWidthMode = MeasureSpec.AT_MOST
                    var childWidthSize = lineWidth
                    if (lp.width == LayoutParams.MATCH_PARENT) {
                        childWidthMode = MeasureSpec.EXACTLY
                    } else if (lp.width >= 0) {
                        childWidthMode = MeasureSpec.EXACTLY
                        childWidthSize = lp.width
                    }
                    child.measure(
                        MeasureSpec.makeMeasureSpec(childWidthSize, childWidthMode),
                        MeasureSpec.makeMeasureSpec(lineHeight - lp.topMargin - lp.bottomMargin, MeasureSpec.EXACTLY)
                    )
                }
                val childWidth: Int = child.measuredWidth
                val childHeight: Int = child.measuredHeight
                val gravityMargin = 0
                child.layout(
                    left + lp.leftMargin,
                    top + lp.topMargin + gravityMargin + verticalGravityMargin,
                    left + childWidth + lp.leftMargin,
                    top + childHeight + lp.topMargin + gravityMargin + verticalGravityMargin
                )
                left += childWidth + lp.leftMargin + lp.rightMargin
            }
            top += lineHeight
        }
    }

    override fun generateLayoutParams(p: LayoutParams) = MarginLayoutParams(p)

    override fun generateLayoutParams(attrs: AttributeSet?) = MarginLayoutParams(context, attrs)

    override fun generateDefaultLayoutParams() = MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)

    override fun checkLayoutParams(p: LayoutParams) = super.checkLayoutParams(p) && p is MarginLayoutParams

}