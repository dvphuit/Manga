package dvp.manga.ui.custom


/**
 * @author dvphu on 20,March,2020
 */

//class OverScrollBounceBehavior : CoordinatorLayout.Behavior<View?> {
//    private var mOverScrollY = 0
//
//    constructor() {}
//    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
//
//    fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout?, child: View?, directTargetChild: View?, target: View?, nestedScrollAxes: Int): Boolean {
//        mOverScrollY = 0
//        return true
//    }
//
//    fun onNestedScroll(coordinatorLayout: CoordinatorLayout?, child: View?, target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int) {
//        if (dyUnconsumed == 0) {
//            return
//        }
//        mOverScrollY -= dyUnconsumed
//        val group = target as ViewGroup
//        val count = group.childCount
//        for (i in 0 until count) {
//            val view: View = group.getChildAt(i)
//            view.setTranslationY(mOverScrollY)
//        }
//    }
//
//    fun onStopNestedScroll(coordinatorLayout: CoordinatorLayout?, child: View?, target: View) {
//        val group = target as ViewGroup
//        val count = group.childCount
//        for (i in 0 until count) {
//            val view: View = group.getChildAt(i)
//            ViewCompat.animate(view).translationY(0f).start()
//        }
//    }
//}