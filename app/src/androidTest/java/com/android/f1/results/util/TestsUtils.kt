package com.android.f1.results.util

import com.android.f1.results.db.FlagsDao
import com.android.f1.results.vo.CountryResponse
import com.android.f1.results.vo.FlagsContent


object TestsUtils {

    fun createFlag(country: String) = CountryResponse(
        FlagsContent(
            country + ".png",
            country + ".svg"
        ),
        country
    )
}