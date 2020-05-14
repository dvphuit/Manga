package dvp.manga

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import dvp.manga.ui.navigation.TabManager
import kotlinx.android.synthetic.main.activity_main.*


/**
 * @author dvphu on 10,March,2020
 */

class MainActivity : AppCompatActivity(),
    BottomNavigationView.OnNavigationItemSelectedListener {

    fun hideBotBar() {
//        if (!bot_nav.isVisible) return
//
//        bot_nav.apply {
//            val animation = TranslateAnimation(0f, 0f, 0f, bot_nav.height.toFloat()).apply {
//                duration = 500
//                fillAfter = true
//            }
//            startAnimation(animation)
//            gone()
//        }
    }

    fun showBotBar() {
//        if (bot_nav.isVisible) return
//
//        bot_nav.apply {
//            val animation = TranslateAnimation(0f, 0f, bot_nav.height.toFloat(), 0f).apply {
//                duration = 500
//                fillAfter = true
//            }
//            startAnimation(animation)
//            visible()
//        }
    }


    private val tabManager: TabManager by lazy { TabManager(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView.setOnNavigationItemSelectedListener(this)

        if (savedInstanceState == null) {
            tabManager.currentController = tabManager.navHomeController
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        tabManager.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        tabManager.onRestoreInstanceState(savedInstanceState)
    }

    override fun supportNavigateUpTo(upIntent: Intent) {
        tabManager.supportNavigateUpTo(upIntent)
    }

    override fun onBackPressed() {
        tabManager.onBackPressed()
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        tabManager.switchTab(menuItem.itemId)
        return true
    }
}
