package dvp.manga

import android.graphics.Color
import android.os.Bundle
import android.transition.TransitionManager
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.top_bar.*

class TestActivity : AppCompatActivity() {

    private var isOpen = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        bt_search.setOnClickListener {
            if (!isOpen) {
                val set = ConstraintSet()
                set.clone(search_bar)
                set.connect(R.id.bt_search, ConstraintSet.START, R.id.search_bar, ConstraintSet.START)
                set.applyTo(search_bar)
                bt_search.setColorFilter(Color.DKGRAY)
                bt_search.background = null
                bt_search.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_arrow_back))
                search_bar.background = ContextCompat.getDrawable(this, R.drawable.bg_search_bar)
            } else {
                val set = ConstraintSet()
                set.clone(search_bar)
                set.connect(R.id.bt_search, ConstraintSet.END, R.id.search_bar, ConstraintSet.END)
                set.applyTo(search_bar)
                bt_search.setColorFilter(Color.WHITE)
                bt_search.background = ContextCompat.getDrawable(this, R.drawable.bg_bt_search)
                bt_search.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_search))
                search_bar.background = null
            }
            isOpen = !isOpen
            TransitionManager.beginDelayedTransition(search_bar)
        }
    }
}
