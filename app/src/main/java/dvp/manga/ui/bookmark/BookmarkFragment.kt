package dvp.manga.ui.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dvp.manga.MainActivity
import dvp.manga.R
import dvp.manga.ui.base.BaseFragment

/**
 * A simple [Fragment] subclass.
 */
class BookmarkFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bookmark, container, false)
    }

    override val withoutBotNav: Boolean = false
}
