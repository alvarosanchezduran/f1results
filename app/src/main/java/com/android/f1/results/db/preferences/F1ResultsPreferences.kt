package com.android.f1.results.db.preferences

import android.content.Context

class F1ResultsPreferences(context: Context): BasePreferences(context, "f1results") {

    fun setFavoriteDriver(driverId: String) {
        putString(USER_FAVORITE_DRIVER_KEY, driverId)
    }

    fun getFavoriteDriver(): String {
        return getString(USER_FAVORITE_DRIVER_KEY, "")
    }

    fun clean() {
        remove(USER_FAVORITE_DRIVER_KEY)
    }

    companion object {
        private const val USER_FAVORITE_DRIVER_KEY = "userFavoriteDriver"
    }
}