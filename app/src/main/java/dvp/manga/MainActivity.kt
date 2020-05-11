package dvp.manga

import android.os.Bundle
import android.view.animation.TranslateAnimation
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import dvp.manga.utils.gone
import dvp.manga.utils.setupWithNavController
import dvp.manga.utils.visible
import kotlinx.android.synthetic.main.activity_main.*


/**
 * @author dvphu on 10,March,2020
 */

class MainActivity : AppCompatActivity() {

    private var currentNavController: LiveData<NavController>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState!!)
        setupBottomNavigationBar()
    }

    private fun setupBottomNavigationBar() {
        val navGraphIds = listOf(
            R.navigation.home_nav,
            R.navigation.explore_nav,
            R.navigation.bookmark_nav,
            R.navigation.setting_nav
        )
        currentNavController = bot_nav.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_container,
            intent = intent
        )
    }

    override fun onSupportNavigateUp() = currentNavController?.value?.navigateUp() ?: false

    fun hideBotBar() {
        if (!bot_nav.isVisible) return

        bot_nav.apply {
            val animation = TranslateAnimation(0f, 0f, 0f, bot_nav.height.toFloat()).apply {
                duration = 500
                fillAfter = true
            }
            startAnimation(animation)
            gone()
        }
    }

    fun showBotBar() {
        if (bot_nav.isVisible) return

        bot_nav.apply {
            val animation = TranslateAnimation(0f, 0f, bot_nav.height.toFloat(), 0f).apply {
                duration = 500
                fillAfter = true
            }
            startAnimation(animation)
            visible()
        }
    }
}
