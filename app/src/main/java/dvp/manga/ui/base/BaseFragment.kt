package dvp.manga.ui.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import dvp.manga.MainActivity


abstract class BaseFragment : Fragment() {

    override fun onPause() {
        super.onPause()
//        (activity as MainActivity).showBotBar()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as MainActivity).hideBotBar()
    }

    fun backPressed() {
        if (activity is MainActivity)
            (activity as MainActivity).onBackPressed()
    }
}