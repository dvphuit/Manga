package dvp.manga.ui.screen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * @author dvphu on 16,March,2020
 */

abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
        setupViews()
        initViewModel()
    }

    abstract fun getLayout(): Int

    abstract fun setupViews()

    abstract fun initViewModel()
}