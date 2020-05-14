package dvp.manga.ui.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import dvp.manga.MainActivity
import dvp.manga.utils.NavManager


abstract class BaseFragment : Fragment() {

    override fun onResume() {
        super.onResume()

        if (withoutBotNav)
            (activity as MainActivity).hideBotBar()
        else
            (activity as MainActivity).showBotBar()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        NavManager.setNavController(requireView().findNavController())
    }

    fun backPressed() {
        if (activity is MainActivity)
            (activity as MainActivity).onBackPressed()
    }

    abstract val withoutBotNav: Boolean
}