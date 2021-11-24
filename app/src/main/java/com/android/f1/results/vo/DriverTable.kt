package com.android.f1.results.vo

import com.google.gson.annotations.SerializedName

data class DriverTable(
    @SerializedName("season")
    val season: String,
    @SerializedName("Drivers")
    val drivers: List<Driver>
)
