package dvp.manga

import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import androidx.appcompat.app.AppCompatActivity
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
        val animation: Animation = TranslateAnimation(0f, 0f, 0f, bot_nav.height.toFloat())
        animation.duration = 500
        animation.fillAfter = true
        bot_nav.startAnimation(animation)
        bot_nav.gone()
    }

    fun showBotBar() {
        val animation: Animation = TranslateAnimation(0f, 0f,  bot_nav.height.toFloat(), 0f)
        animation.duration = 500
        animation.fillAfter = true
        bot_nav.startAnimation(animation)
        bot_nav.visible()
    }
}
