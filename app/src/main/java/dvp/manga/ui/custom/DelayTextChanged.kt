package dvp.manga.ui.custom

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.util.*

abstract class DelayTextChanged : TextWatcher {
    private var timer = Timer()
    private val delay: Long = 300
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

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
}

fun EditText.onTextChanged(listener: (String) -> Unit) {
    this.addTextChangedListener(object : DelayTextChanged() {
        override fun textWasChanged(s: String) {
            if (s.length > 3)
                listener(s)
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

    })
}