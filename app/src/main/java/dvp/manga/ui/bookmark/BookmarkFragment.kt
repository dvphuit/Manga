package dvp.manga.ui.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dvp.manga.MainActivity
import dvp.manga.R

/**
 * A simple [Fragment] subclass.
 */
class BookmarkFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bookmark, container, false)
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).showBotBar()
    }
}
