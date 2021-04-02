package id.com.android.weatherfinder.controller

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

class ControllerPreference(application : Application) {

    companion object {
        var sharedPreference : SharedPreferences? =  null
    }

    init {
        sharedPreference        = application.getSharedPreferences("Preference", Context.MODE_PRIVATE)
        val editor              = sharedPreference!!.edit()
        editor.apply()
    }

    fun setPref(KEY: String, value: Any) {
        val editor = sharedPreference!!.edit()
        when (value) {
            is Boolean -> editor.putBoolean(KEY,    value)
        }
        editor.apply()
    }

    fun clearPref(KEY: String) {
        val editor = sharedPreference!!.edit()
        editor.remove(KEY)
        editor.apply()
    }

    fun getBoolean(key: String, value: Boolean): Boolean {
        return sharedPreference!!.getBoolean(key, value)
    }

}
