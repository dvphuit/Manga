package dvp.manga

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import dvp.manga.databinding.FragmentBlankBinding


/**
 * A simple [Fragment] subclass.
 */
class BlankFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentBlankBinding.inflate(inflater, container, false)
        context ?: return binding.root
        with(binding) {
            searchback.setOnClickListener {
                //                val element1 = android.util.Pair<View, String>(searchback, searchback.transitionName)
//                val element2 = android.util.Pair<View, String>(binding.searchbackContainer, searchbackContainer.transitionName)
//                val options = ActivityOptions.makeSceneTransitionAnimation(activity, element1, element2).toBundle()
//                startActivity(Intent(requireContext(), SearchActivity::class.java), options)

                gotoSearch(searchback, searchBar)
            }
        }

        return binding.root
    }


    private fun gotoSearch(vararg view: View) {
        val extras = FragmentNavigatorExtras(
            view[0] to view[0].transitionName,
            view[1] to view[1].transitionName
        )
        val direction = BlankFragmentDirections.gotoSearch()
        view[0].findNavController().navigate(direction, extras)
    }
}
