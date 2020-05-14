package dvp.manga.ui.navigation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isInvisible
import androidx.navigation.NavController
import androidx.navigation.findNavController
import dvp.manga.MainActivity
import dvp.manga.R
import kotlinx.android.synthetic.main.activity_main.*

class TabManager(private val mainActivity: MainActivity) {

    private val startDestinations = mapOf(
        R.id.home to R.id.homeFragment,
        R.id.explore to R.id.exploreFragment,
        R.id.bookmark to R.id.bookmarkFragment,
        R.id.setting to R.id.settingFragment
    )


    private var currentTabId: Int = R.id.home
    var currentController: NavController? = null
    private var tabHistory = TabHistory().apply { push(R.id.home) }

    val navHomeController: NavController by lazy {
        mainActivity.findNavController(R.id.homeTab).apply {
            graph = navInflater.inflate(R.navigation.home_nav).apply {
                startDestination = startDestinations.getValue(R.id.home)
            }
        }
    }
    private val navExploreController: NavController by lazy {
        mainActivity.findNavController(R.id.exploreTab).apply {
            graph = navInflater.inflate(R.navigation.home_nav).apply {
                startDestination = startDestinations.getValue(R.id.explore)
            }
        }
    }
    private val navBookmarkController: NavController by lazy {
        mainActivity.findNavController(R.id.bookmarkTab).apply {
            graph = navInflater.inflate(R.navigation.home_nav).apply {
                startDestination = startDestinations.getValue(R.id.bookmark)
            }
        }
    }

    private val navSettingController: NavController by lazy {
        mainActivity.findNavController(R.id.settingTab).apply {
            graph = navInflater.inflate(R.navigation.home_nav).apply {
                startDestination = startDestinations.getValue(R.id.setting)
            }
        }
    }

    private val homeTabContainer: View by lazy { mainActivity.homeTabContainer }
    private val exploreTabContainer: View by lazy { mainActivity.exploreTabContainer }
    private val bookmarkTabContainer: View by lazy { mainActivity.bookmarkTabContainer }
    private val settingTabContainer: View by lazy { mainActivity.settingTabContainer }

    fun onSaveInstanceState(outState: Bundle?) {
        outState?.putSerializable(KEY_TAB_HISTORY, tabHistory)
    }

    fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            tabHistory = it.getSerializable(KEY_TAB_HISTORY) as TabHistory

            switchTab(mainActivity.bottomNavigationView.selectedItemId, false)
        }
    }

    fun supportNavigateUpTo(upIntent: Intent) {
        currentController?.navigateUp()
    }

    fun onBackPressed() {
        currentController?.let {
            if (it.currentDestination == null || it.currentDestination?.id == startDestinations.getValue(currentTabId)) {
                if (tabHistory.size > 1) {
                    val tabId = tabHistory.popPrevious()
                    switchTab(tabId, false)
                    mainActivity.bottomNavigationView.menu.findItem(tabId)?.isChecked = true
                } else {
                    mainActivity.finish()
                }
            }
            it.popBackStack()
        } ?: run {
            mainActivity.finish()
        }
    }

    fun switchTab(tabId: Int, addToHistory: Boolean = true) {
        currentTabId = tabId

        when (tabId) {
            R.id.home -> {
                currentController = navHomeController
                invisibleTabContainerExcept(homeTabContainer)
            }
            R.id.explore -> {
                currentController = navExploreController
                invisibleTabContainerExcept(exploreTabContainer)
            }
            R.id.bookmark -> {
                currentController = navBookmarkController
                invisibleTabContainerExcept(bookmarkTabContainer)
            }
            R.id.setting -> {
                currentController = navSettingController
                invisibleTabContainerExcept(settingTabContainer)
            }
        }
        if (addToHistory) {
            tabHistory.push(tabId)
        }
    }

    private fun invisibleTabContainerExcept(container: View) {
        homeTabContainer.isInvisible = true
        exploreTabContainer.isInvisible = true
        bookmarkTabContainer.isInvisible = true
        settingTabContainer.isInvisible = true

        container.isInvisible = false
    }

    companion object {
        private const val KEY_TAB_HISTORY = "key_tab_history"
    }
}