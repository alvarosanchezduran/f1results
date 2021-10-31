package com.android.f1.results.vo

import com.google.gson.annotations.SerializedName

data class AverageSpeed(
    @SerializedName("units")
    val units: String,
    @SerializedName("speed")
    val speed: String
)
