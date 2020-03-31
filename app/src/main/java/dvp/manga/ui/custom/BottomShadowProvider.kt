package dvp.manga.ui.custom

import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider

/**
 * @author dvphu on 31,March,2020
 */
open class BottomShadowProvider : ViewOutlineProvider(){
    override fun getOutline(view: View?, outline: Outline?) {
        outline!!.setRoundRect(view!!.left, view.top, view.width - view.left, view.height, view.height.toFloat())
    }
}