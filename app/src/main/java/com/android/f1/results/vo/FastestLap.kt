package com.android.f1.results.vo

import com.google.gson.annotations.SerializedName

data class FastestLap(
    @SerializedName("rank")
    val rank: String,
    @SerializedName("lap")
    val lap: String,
    @SerializedName("Time")
    val time: TimeFastestLap,
    @SerializedName("AverageSpeed")
    val AverageSpeed: AverageSpeed
)

data class TimeFastestLap(
    @SerializedName("time")
    val time: String
)
