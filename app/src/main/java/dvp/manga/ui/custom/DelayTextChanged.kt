package dvp.manga.ui.custom

import android.text.Editable
import android.text.TextWatcher
import java.util.*

abstract class DelayTextChanged : TextWatcher {
    private var timer = Timer()
    private val delay: Long = 200
    override fun afterTextChanged(s: Editable?) {
        timer.cancel()
        timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                textWasChanged(s.toString())
            }
        }, delay)
    }

    abstract fun textWasChanged(s: String)

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int){}
}