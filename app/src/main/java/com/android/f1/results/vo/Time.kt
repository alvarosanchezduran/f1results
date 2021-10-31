package com.android.f1.results.vo

import com.google.gson.annotations.SerializedName

data class Time(
    @SerializedName("millis")
    val millis: String,
    @SerializedName("time")
    val time: String
)
