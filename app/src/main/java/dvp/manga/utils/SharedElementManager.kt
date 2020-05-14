package dvp.manga.utils

import android.util.Log
import android.view.ViewTreeObserver
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import dvp.manga.data.model.SectionRoute

/**
 * @author dvphu on 12,May,2020
 */

object SharedElementManager {
    private var currentFragment: Fragment? = null

    private var elementPosition = -1
    private var transitionName = ""
    private var sectionRoute: SectionRoute? = null

    fun setElementInfo(name: String, position: Int) {
        this.transitionName = name
        this.elementPosition = position
    }

    fun setRoute(route: SectionRoute) {
        this.sectionRoute = route
    }

    fun postSE(fragment: Fragment) {
        if ((transitionName != "" && elementPosition != -1) || sectionRoute != null) {
            Log.d("TEST", "post $transitionName -- $elementPosition -- $sectionRoute")
            currentFragment = fragment.also {
                it.postponeEnterTransition()
            }
        }
    }

    fun startSE(name: String, position: Int) {
        Log.d("TEST", "start route $name - $position")
        if (name == transitionName && position == elementPosition) {
            startSE()
            elementPosition = -1
            transitionName = ""
        }
    }

    fun startSE(route: SectionRoute) {
        Log.d("TEST", "start route $route")
        if (route == sectionRoute) {
            startSE()
            sectionRoute = null
        }
    }

    fun startSE(recyclerView: RecyclerView) {
        recyclerView.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                startSE()
                recyclerView.viewTreeObserver.removeOnPreDrawListener(this)
                return true
            }
        })
    }

    fun startSE() {
        currentFragment?.startPostponedEnterTransition()
    }
}