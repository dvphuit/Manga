package dvp.manga.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor


/**
 * @author dvphu on 16,March,2020
 */

object SharedPref {
    const val PREF_NAME = "manga.pref"
    private var sharedPreferences: SharedPreferences? = null
    private var editor: Editor? = null
    @SuppressLint("CommitPrefEdits")
    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        editor = sharedPreferences!!.edit()
    }

    fun setStringValue(key: String?, value: String?) {
        editor!!.putString(key, value)
        editor!!.apply()
    }

    fun getString(key: String?): String? {
        return sharedPreferences!!.getString(key, "")
    }

    fun setIntValue(key: String?, value: Int) {
        editor!!.putInt(key, value)
        editor!!.apply()
    }

    fun getInt(key: String?): Int {
        return sharedPreferences!!.getInt(key, -1)
    }

    fun clearValue() {
        editor!!.clear()
    }
}