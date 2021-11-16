package com.android.f1.results.util

import android.graphics.Color
import com.android.f1.results.R
import java.util.*

object ConstructorsColors {

    val CONSTRUCTORS_COLORS_PROVISIONAL: MutableMap<String, Int> = mutableMapOf()

    val CONSTRUCTORS_COLORS: MutableMap<String, Int> = mutableMapOf(
        "alfa" to R.color.alfa_romeo,
        "alphatauri" to R.color.alpha_tauri,
        "alpine" to R.color.alpine,
        "aston_martin" to R.color.aston_martin,
        "ferrari" to R.color.ferrari,
        "haas" to R.color.haas,
        "mclaren" to R.color.mclaren,
        "mercedes" to R.color.mercedes,
        "red_bull" to R.color.red_bull,
        "williams" to R.color.williams
    )

    fun getConstructorColorSaved(team: String): Int? {
        return CONSTRUCTORS_COLORS[team]
    }

    fun getConstructorColorProvisional(team: String): Int {
        CONSTRUCTORS_COLORS_PROVISIONAL[team]?.let {
            return it
        }?: run {
            val rnd = Random()
            val color: Int = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
            CONSTRUCTORS_COLORS_PROVISIONAL[team] = color
            return color
        }
    }
}