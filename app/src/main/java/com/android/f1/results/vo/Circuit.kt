package com.android.f1.results.vo

import com.google.gson.annotations.SerializedName

data class Circuit(
    @SerializedName("circuitId")
    val circuitId: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("circuitName")
    val circuitName: String,
    @SerializedName("Location")
    val location: Location,
)