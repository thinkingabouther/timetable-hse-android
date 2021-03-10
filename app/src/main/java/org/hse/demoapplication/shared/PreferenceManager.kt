package org.hse.demoapplication.shared

import android.content.Context
import org.hse.demoapplication.R

class PreferenceManager(context: Context) {
    private val PREFERENCE_FILE = "org.hse.android.file"
    private var sharedPreferences =
        context.getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE)

    fun set(key : String, value : String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun get(key : String) : String? {
        return sharedPreferences.getString(key,
            R.string.sharedPreferencesGetDefaultValue.toString()
        )
    }
}