package dvp.manga

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import dvp.manga.ui.home.HomeFragment
import okhttp3.OkHttpClient
import okhttp3.Request


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
