package dvp.manga

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dvp.manga.ui.home.HomeFragment

/**
 * @author dvphu on 10,March,2020
 */

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, HomeFragment.newInstance())
                .commitNow()
        }
    }
}
