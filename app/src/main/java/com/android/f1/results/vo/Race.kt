package com.android.f1.results.vo

import com.google.gson.annotations.SerializedName

data class Race(
    @SerializedName("season")
    val season: String,
    @SerializedName("round")
    val round: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("raceName")
    val raceName: String,
    @SerializedName("Circuit")
    val circuit: Circuit,
    @SerializedName("date")
    val date: String,
    @SerializedName("time")
    val time: String,
    @SerializedName("Results")
    val results: List<Result>?
)