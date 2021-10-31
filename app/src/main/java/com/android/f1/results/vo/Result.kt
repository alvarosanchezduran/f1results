package com.android.f1.results.vo

import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("number")
    val number: String,
    @SerializedName("position")
    val position: String,
    @SerializedName("positionText")
    val positionText: String,
    @SerializedName("points")
    val points: String,
    @SerializedName("Driver")
    val driver: Driver,
    @SerializedName("Constructor")
    val constructor: Constructor,
    @SerializedName("grid")
    val grid: String,
    @SerializedName("laps")
    val laps: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("Time")
    val time: Time,
    @SerializedName("FastestLap")
    val fastestLap: FastestLap,
)
