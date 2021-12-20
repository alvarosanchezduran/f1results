package com.android.f1.results.db.preferences

import android.content.Context
import android.content.SharedPreferences
import java.util.*

abstract class BasePreferences constructor(context: Context?, preferencesName: String) {

    private lateinit var sharedPreferences: SharedPreferences

    init {
        context?.let {
            sharedPreferences = context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE)
        }
    }

    protected fun putInt(key: String, value: Int) {
        getEditor()
            .putInt(key, value)
            .apply()
    }

    protected fun putLong(key: String, value: Long) {
        getEditor()
            .putLong(key, value)
            .apply()
    }

    protected fun putString(key: String, value: String) {
        getEditor()
            .putString(key, value)
            .apply()
    }

    protected fun putBoolean(key: String, value: Boolean) {
        getEditor()
            .putBoolean(key, value)
            .apply()
    }

    protected fun remove(key: String) {
        getEditor()
            .remove(key)
            .apply()
    }

    protected fun getString(key: String, defaultValue: String = ""): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }

    protected fun getInt(key: String, defaultValue: Int = 0): Int {
        return sharedPreferences.getInt(key, defaultValue)
    }

    protected fun getLong(key: String, defaultValue: Long = 0): Long {
        return sharedPreferences.getLong(key, defaultValue)
    }

    protected fun getBoolean(key: String, defaultValue: Boolean = false) : Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    protected fun putDate(key: String, date: Date) {
        getEditor()
            .putLong(key, date.time)
            .apply()
    }

    protected fun getDate(key: String): Date? {
        val dateLong = sharedPreferences.getLong(key, 0)
        return if (dateLong > 0 ) Date(dateLong)
        else null
    }

    protected fun clearPreferences() {
        getEditor()
            .clear()
            .apply()
    }

    private fun getEditor(): SharedPreferences.Editor {
        return sharedPreferences.edit()
    }

}