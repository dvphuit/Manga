package dvp.manga.utils

import android.content.Context
import android.net.ConnectivityManager

/**
 * @author dvphu on 16,March,2020
 */

fun Context.isOnline() : Boolean{
    val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return cm.allNetworks.isEmpty()
}