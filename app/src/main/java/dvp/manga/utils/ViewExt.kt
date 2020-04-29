package dvp.manga.utils

import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.delayForSharedElement(fragment: Fragment) {
    viewTreeObserver.addOnPreDrawListener {
        fragment.startPostponedEnterTransition()
        true
    }
}

fun View.visible(){
    visibility = View.VISIBLE
}

fun View.gone(){
    visibility = View.GONE
}