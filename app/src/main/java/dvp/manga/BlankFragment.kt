package dvp.manga

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Pair
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.fragment.app.Fragment
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
        activity!!.window.sharedElementEnterTransition.duration = 2000
        activity!!.window.sharedElementReturnTransition.setDuration(2000).interpolator = DecelerateInterpolator()
        bt_search.setOnClickListener {
            val element = Pair<View, String>(bt_search, "bt_search")
            val options = ActivityOptions.makeSceneTransitionAnimation(activity, element)
            val i = Intent(activity, SearchActivity::class.java)
            i.putExtra("item_name", "item 2")
            startActivity(i, options.toBundle())
        }
    }
}
