package dvp.manga.utils

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.delayForSharedElement(fragment: Fragment) {
    viewTreeObserver.addOnPreDrawListener {
        fragment.startPostponedEnterTransition()
        true
    }
}