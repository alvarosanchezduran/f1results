package com.android.f1.results.vo

import com.google.gson.annotations.SerializedName

data class CircuitTable(
    @SerializedName("season")
    val season: String,
    @SerializedName("Circuits")
    val circuits: List<Circuit>
)