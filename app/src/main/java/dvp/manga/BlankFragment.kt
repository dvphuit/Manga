package dvp.manga

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
            }
        }
        return binding.root
    }


}
