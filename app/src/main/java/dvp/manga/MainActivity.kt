package dvp.manga

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import dvp.manga.databinding.ActivityMainBinding


/**
 * @author dvphu on 10,March,2020
 */

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }
}
