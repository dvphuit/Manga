package dvp.manga

import android.app.SharedElementCallback
import android.graphics.Point
import android.os.Bundle
import android.transition.Transition
import android.transition.TransitionSet
import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity

class SearchActivity : AppCompatActivity() {

    private var isEnter = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setupTransitions()
    }


    private fun setupTransitions() {
        // grab the position that the search icon transitions in *from*
        // & use it to configure the return transition
        setEnterSharedElementCallback(object : SharedElementCallback() {
            override fun onSharedElementStart(
                sharedElementNames: List<String>,
                sharedElements: List<View>?,
                sharedElementSnapshots: List<View>
            ) {
                if (sharedElements != null && sharedElements.isNotEmpty()) {
//                    if (isEnter){
//                        isEnter = false
//                        val anim = ValueAnimator()
//                        anim.setIntValues(Color.parseColor("#FF9800"), Color.parseColor("#FFFFFF"))
//                        anim.setEvaluator(ArgbEvaluator())
//                        anim.addUpdateListener {
//                            val value = it.animatedValue as Int
//                            val background = search_toolbar.background as GradientDrawable
//                            background.setColor(value)
//                        }
//                        anim.duration = 400
//                        anim.start()
//                    }else{
//                        val anim = ValueAnimator()
//                        anim.setIntValues(Color.parseColor("#FFFFFF"), Color.parseColor("#FF9800"))
//                        anim.setEvaluator(ArgbEvaluator())
//                        anim.addUpdateListener {
//                            val value = it.animatedValue as Int
//                            val background = search_toolbar.background as GradientDrawable
//                            background.setColor(value)
//                        }
//                        anim.duration = 400
//                        anim.start()
//                    }

                    val searchIcon = sharedElements[0]
                    if (searchIcon.id != R.id.searchback) return
                    val centerX = (searchIcon.left + searchIcon.right) / 2
                    val hideResults = findTransition(
                        window.returnTransition as TransitionSet,
                        CircularReveal::class.java, R.id.results_container
                    ) as CircularReveal?
                    hideResults?.setCenter(Point(centerX, 0))
                }
            }
        })
    }

    internal fun findTransition(
        set: TransitionSet,
        clazz: Class<out Transition?>,
        @IdRes targetId: Int
    ): Transition? {
        for (i in 0 until set.transitionCount) {
            val transition = set.getTransitionAt(i)
            if (transition.javaClass == clazz) {
                if (transition.targetIds.contains(targetId)) {
                    return transition
                }
            }
            if (transition is TransitionSet) {
                val child = findTransition(transition, clazz, targetId)
                if (child != null) return child
            }
        }
        return null
    }
}
