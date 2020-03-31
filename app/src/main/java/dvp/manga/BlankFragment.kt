package dvp.manga

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Pair
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dvp.manga.ui.search.SearchActivity
import kotlinx.android.synthetic.main.fragment_blank.*


/**
 * A simple [Fragment] subclass.
 */
class BlankFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_blank, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchback.setOnClickListener {
            val element1 = Pair<View, String>(searchback, searchback.transitionName)
            val element2 = Pair<View, String>(searchback_container, searchback_container.transitionName)
            val options = ActivityOptions.makeSceneTransitionAnimation(activity, element1, element2).toBundle()
            startActivity(Intent(context, SearchActivity::class.java), options)
        }
    }
}
