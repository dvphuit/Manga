package dvp.manga.utils

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
        currentFragment = fragment.also {
            it.postponeEnterTransition()
        }
    }

    fun startSE(position: Int) {
        if (position == elementPosition)
            startSE()
    }

    fun startSE(route: SectionRoute) {
        if (route == sectionRoute)
            startSE()
    }

    fun startSE(recyclerView: RecyclerView) {
        recyclerView.viewTreeObserver.addOnPreDrawListener {
            startSE()
            true
        }
    }

    fun startSE() {
        currentFragment?.startPostponedEnterTransition()
    }
}