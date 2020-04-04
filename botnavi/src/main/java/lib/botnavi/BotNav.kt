package lib.botnavi

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.animation.DecelerateInterpolator
import android.view.animation.OvershootInterpolator
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.constraintlayout.widget.ConstraintSet.*
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.core.view.get
import lib.botnavi.botnavi.R


class BotNav(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs),
    View.OnClickListener {

    var selectedItemId: Int = 0

    private var selectedPosition = 0
    private var prePosition = -1
    private val menus: List<Menu>

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.BotNav)
        val menu = a.getResourceId(R.styleable.BotNav_menu, -1)
        menus = MenuParser(context, menu).menus
        initMenu()
        a.recycle()
    }

    private fun initMenu() {
        menus.forEach { addChild(it.id, it.title, it.icon, it.color) }
        alignCenterVertical()
        alignStartEndViews()
        setConstraintEachChild()
        onClick(this[0])
    }

    private fun alignStartEndViews() {
        val set1 = ConstraintSet()

        set1.clone(this)
        val firstViewId = this[0].id
        set1.connect(firstViewId, START, this.id, START, 0)
        set1.setHorizontalChainStyle(firstViewId, CHAIN_PACKED)
        set1.applyTo(this)

        val set2 = ConstraintSet()
        set2.clone(this)
        val lastViewId = this[this.childCount - 1].id
        set2.connect(lastViewId, END, this.id, END, 0)
        set2.applyTo(this)
    }

    private fun alignCenterVertical() {
        for (i in menus.indices) {
            val set = ConstraintSet()
            val viewId = this[i].id
            set.clone(this)
            set.centerVertically(viewId, this.id)
            set.connect(viewId, BOTTOM, this.id, BOTTOM, 0)
            set.connect(viewId, TOP, this.id, TOP, 0)
            set.applyTo(this)
        }
    }

    private fun setConstraintEachChild() {
        for (i in 0 until menus.lastIndex) {
            val set = ConstraintSet()
            val curViewId = this[i].id
            val nextViewId = this[i + 1].id
            set.clone(this)
            set.connect(curViewId, END, nextViewId, START, 8.dp)
            set.connect(nextViewId, START, curViewId, END, 8.dp)
            set.applyTo(this)
        }
    }

    private fun addChild(id: Int, title: CharSequence, icon: Int, color: Int) {
        val img = ImageView(context)
        img.apply {
            setImageResource(icon)
            setColorFilter(Color.DKGRAY)
            val imgParams = LinearLayout.LayoutParams(20.dp, 20.dp)
            imgParams.setMargins(8.dp, 0, 8.dp, 0)
            layoutParams = imgParams
        }

        val tv = TextView(context)
        tv.apply {
            gone()
            text = title
            setTextColor(color)
            typeface = Typeface.DEFAULT_BOLD
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
            val tvParams = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
            tvParams.setMargins(0.dp, 0, 8.dp, 0)
            layoutParams = tvParams
        }

        val group = LinearLayout(context)
        group.apply {
            this.id = id
            gravity = Gravity.CENTER
            orientation = LinearLayout.HORIZONTAL
            layoutParams = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
                setMargins(4.dp, 0, 4.dp, 0)
                setPadding(8.dp, 8.dp, 8.dp, 8.dp)
            }
            background = ContextCompat.getDrawable(context, R.drawable.bg_bot_navi_item)
            setOnClickListener(this@BotNav)
            addView(img)
            addView(tv)
        }

        this.addView(group)
    }

    private fun unHighlight(index: Int) {
        if (index == -1) return
        val parent = this[index] as ViewGroup
        (parent.background as GradientDrawable).color = null
        parent[1].gone()
        (parent[0] as ImageView).setColorFilter(Color.DKGRAY)
    }

    private fun highlight(index: Int) {
        val parent = this[index] as ViewGroup
        val tv = parent[1] as TextView
        tv.visible()
        val anim = ValueAnimator()
        anim.setIntValues(Color.DKGRAY, menus[index].color)
        anim.setEvaluator(ArgbEvaluator())
        anim.addUpdateListener {
            val value = it.animatedValue as Int
            (parent.background as GradientDrawable).setColor(value.lighten)
            tv.setTextColor(value)
            (parent[0] as ImageView).setColorFilter(value)
        }
        anim.duration = 0
        anim.start()
    }

    override fun onClick(v: View?) {
        selectedPosition = this.indexOfChild(v)
        listener?.onPreviousTabSelected(prePosition)
        unHighlight(prePosition)
        if (prePosition == selectedPosition) {
            listener?.onTabReselected(selectedPosition, selectedItemId)
            highlight(selectedPosition)
        }
        if (prePosition != selectedPosition) {
            selectedItemId = v!!.id
            prePosition = selectedPosition
            listener?.onTabSelected(selectedPosition, selectedItemId)
            highlight(selectedPosition)
            overShotTransition(this@BotNav)
        }
    }

    fun setSelectedItem(id: Int) {
        selectedItemId = id
        onClick(findViewById(id))
    }

    private var listener: TabSelectedListener? = null

    fun addOnTabSelectedListener(listener: TabSelectedListener) {
        this.listener = listener
    }

    abstract class TabSelectedListener {
        open fun onTabSelected(position: Int, id: Int) {}
        open fun onTabReselected(position: Int, id: Int) {}
        open fun onPreviousTabSelected(position: Int) {}
    }

    //region extension
    private fun overShotTransition(root: ViewGroup) {
        val transition = ChangeBounds()
        transition.interpolator = OvershootInterpolator()
        transition.duration = 300
        TransitionManager.beginDelayedTransition(root, transition)
    }

    private inline val Int.dp: Int
        get() = (this * Resources.getSystem().displayMetrics.density).toInt()

    private fun View.visible() {
        this.visibility = View.VISIBLE
    }

    private fun View.gone() {
        this.visibility = View.GONE
    }

    inline val @receiver:ColorInt Int.lighten
        @ColorInt
        get() = ColorUtils.blendARGB(this, Color.WHITE, 0.8f)
    //endregion
}