package id.com.android.laundry.controller

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import org.json.JSONArray
import org.json.JSONException
import java.util.ArrayList

class ControllerPreference(application : Application) {

    companion object {
        var sharedPreference : SharedPreferences? =  null
    }

    init {
        sharedPreference        = application.getSharedPreferences("Preference", Context.MODE_PRIVATE)
        val editor              = sharedPreference!!.edit()
        editor.apply()
    }

    fun getString(key: String, value: String): String {
        return sharedPreference!!.getString(key, value)
    }

    fun getInt(key: String, value: Int): Int {
        return sharedPreference!!.getInt(key, value)
    }

    fun getLong(key: String, value: Long): Long {
        return sharedPreference!!.getLong(key, value)
    }

    fun getBoolean(key: String, value: Boolean): Boolean {
        return sharedPreference!!.getBoolean(key, value)
    }

    fun getArrayString(key: String): ArrayList<String> {
        val array       = ArrayList<String>()
        val jsonArray   = sharedPreference!!.getString(key, key)

        if (jsonArray.matches(key.toRegex())) {
            return array
        }
        else {
            try {
                val jArray = JSONArray(jsonArray)
                (0 until jArray.length()).mapTo(array) { jArray.getString(it) }
                return array
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        return array
    }

    fun setPref(KEY: String, value: Any) {
        val editor = sharedPreference!!.edit()
        when (value) {
            is String -> editor.putString(KEY,      value)
            is Boolean -> editor.putBoolean(KEY,    value)
            is Int -> editor.putInt(KEY,            value)
            is Long -> editor.putLong(KEY,          value)
            is ArrayList<*> -> {
                val jsonArray = JSONArray(value)
                editor.putString(KEY, jsonArray.toString())
            }
        }
        editor.apply()
    }

    fun clearPref(KEY: String) {
        val editor = sharedPreference!!.edit()
        editor.remove(KEY)
        editor.apply()
    }


}
