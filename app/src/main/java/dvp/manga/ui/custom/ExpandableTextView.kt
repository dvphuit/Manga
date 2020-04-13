package dvp.manga.ui.custom


/**
 * @author dvphu on 4/14/2017.
 */
//class ExpandableTextView(context: Context, attrs: AttributeSet?, defStyle: Int) : AppCompatTextView(context, attrs, defStyle), View.OnClickListener {
//    private val expandInterpolator: TimeInterpolator
//    private val collapseInterpolator: TimeInterpolator
//    private var maxLines: Int = 3
//    private val animationDuration: Long
//    private var animating = false
//    private var expanded = false
//    private var originalHeight = 0
//
//    constructor(context: Context) : this(context, null)
//    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
//
//    /**
//     * Toggle the expanded state of this [ExpandableTextView].
//     *
//     * @return true if toggled, false otherwise.
//     */
//    fun toggle(): Boolean {
//        changeDrawable()
//        return if (expanded) {
//            collapse()
//        } else expand()
//    }
//
//    private var arrowUp: Drawable? = null
//    private var arrowDown: Drawable? = null
//    val ellipsizedHeight: Int
//        get() = if (isEllipsized) {
//            val dm: AppCompatDrawableManager = AppCompatDrawableManager.get()
//            arrowUp = dm.getDrawable(context, R.drawable.ic_up_arrow)
//            arrowDown = dm.getDrawable(context, R.drawable.ic_down_arrow)
//            setCompoundDrawablesWithIntrinsicBounds(null, null, null, arrowDown)
//            height + compoundDrawables.get(3).intrinsicHeight
//        } else height
//
//    private fun changeDrawable() {
//        if (compoundDrawables.get(3) == null) return
//        setCompoundDrawablesWithIntrinsicBounds(null, null, null, if (expanded) arrowUp else arrowDown)
//    }
//
//    val isEllipsized: Boolean
//        get() {
//            if (layout != null) {
//                val lines: Int = layout.lineCount
//                if (lines > 0) {
//                    val ellipsisCount: Int = layout.getEllipsisCount(lines - 1)
//                    if (ellipsisCount > 0) {
//                        return true
//                    }
//                }
//            }
//            return false
//        }
//
//    /**
//     * Expand this [ExpandableTextView].
//     *
//     * @return true if expanded, false otherwise.
//     */
//    fun expand(): Boolean {
//        if (!expanded && !animating && maxLines >= 0 && isEllipsized) {
//            animating = true
//            // get original height
//            this.measure(
//                MeasureSpec.makeMeasureSpec(this.measuredWidth, MeasureSpec.EXACTLY),
//                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
//            )
//            originalHeight = this.measuredHeight
//            // set maxLines to MAX Integer
//            this.setMaxLines(Int.MAX_VALUE)
//            // get new height
//            this.measure(
//                MeasureSpec.makeMeasureSpec(this.measuredWidth, MeasureSpec.EXACTLY),
//                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
//            )
//            val fullHeight: Int = this.measuredHeight
//            val valueAnimator = ValueAnimator.ofInt(originalHeight, fullHeight)
//            valueAnimator.addUpdateListener { animation ->
//                if (view != null) {
//                    val params: ViewGroup.LayoutParams = view.getLayoutParams()
//                    params.height = imageViewHeightOriginal + animation.animatedValue as Int
//                    view.setLayoutParams(params)
//                }
//                val layoutParams: ViewGroup.LayoutParams = this@ExpandableTextView.layoutParams
//                layoutParams.height = animation.animatedValue as Int
//                this@ExpandableTextView.layoutParams = layoutParams
//            }
//            valueAnimator.addListener(object : AnimatorListenerAdapter() {
//                override fun onAnimationEnd(animation: Animator) {
//                    expanded = true
//                    animating = false
//                    if (listener != null) listener!!.onExpanded()
//                }
//            })
//            // set interpolator
//            valueAnimator.interpolator = expandInterpolator
//            // start the animation
//            valueAnimator.setDuration(animationDuration).start()
//            return true
//        }
//        return false
//    }
//
//    private var view: View? = null
//    private var imageViewHeightOriginal = 0
//    fun updateParentView(view: View?, originalHeight: Int) {
//        this.view = view
//        imageViewHeightOriginal = originalHeight
//    }
//
//    /**
//     * Collapse this [TextView].
//     *
//     * @return true if collapsed, false otherwise.
//     */
//    fun collapse(): Boolean {
//        if (expanded && !animating && maxLines >= 0) {
//            animating = true
//            // get new height
//            val fullHeight: Int = this.measuredHeight
//            val valueAnimator = ValueAnimator.ofInt(fullHeight, originalHeight)
//            valueAnimator.addUpdateListener { animation ->
//                if (view != null) {
//                    val params: ViewGroup.LayoutParams = view.getLayoutParams()
//                    params.height = imageViewHeightOriginal + animation.animatedValue as Int
//                    view.setLayoutParams(params)
//                }
//                val layoutParams: ViewGroup.LayoutParams = this@ExpandableTextView.layoutParams
//                layoutParams.height = animation.animatedValue as Int
//                this@ExpandableTextView.layoutParams = layoutParams
//            }
//            valueAnimator.addListener(object : AnimatorListenerAdapter() {
//                override fun onAnimationEnd(animation: Animator) { // set maxLines to original value
//                    this@ExpandableTextView.setMaxLines(maxLines)
//                    this@ExpandableTextView.ellipsize = TextUtils.TruncateAt.END
//                    expanded = false
//                    animating = false
//                    if (listener != null) listener!!.onCollapsed()
//                }
//            })
//            // set interpolator
//            valueAnimator.interpolator = collapseInterpolator
//            // start the animation
//            valueAnimator.setDuration(animationDuration).start()
//            return true
//        }
//        return false
//    }
//
//    fun onClick(view: View?) {
//        toggle()
//    }
//
//    private var listener: OnExpandListener? = null
//    fun setOnExpandListener(listener: OnExpandListener?) {
//        this.listener = listener
//    }
//
//    interface OnExpandListener {
//        fun onExpanded()
//        fun onCollapsed()
//    }
//
//    init {
//        // read attributes
//        val attributes: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.ExpandableTextView, defStyle, 0)
//        animationDuration = attributes.getInt(R.styleable.ExpandableTextView_animation_duration, 300).toLong()
//        attributes.recycle()
//        // keep the original value of maxLines
//        maxLines = getMaxLines()
//        // create default interpolator
//        expandInterpolator = AccelerateDecelerateInterpolator()
//        collapseInterpolator = AccelerateDecelerateInterpolator()
//        setOnClickListener(this)
//    }
//}