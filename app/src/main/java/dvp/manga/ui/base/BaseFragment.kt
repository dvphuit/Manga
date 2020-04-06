package dvp.manga.ui.base

import androidx.fragment.app.Fragment


abstract class BaseFragment: Fragment() {

    override fun onPause() {
        super.onPause()
        view?.translationZ = -1f // trick replace fragment, bring to back new one
    }
}