package dvp.manga.ui.custom

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import androidx.constraintlayout.motion.widget.MotionLayout

class ExtMotionLayout : MotionLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onSaveInstanceState(): Parcelable? {
        return SavedState(progress, super.onSaveInstanceState())
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is SavedState) {
            progress = state.progress
            super.onRestoreInstanceState(state.superState)
        } else super.onRestoreInstanceState(state)
    }

    class SavedState : BaseSavedState {
        val progress: Float

        constructor(progress: Float, source: Parcelable?) : super(source) {
            this.progress = progress
        }

        constructor(superState: Parcel) : super(superState) {
            progress = superState.readFloat()
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeFloat(progress)
        }
    }
}