package com.android.f1.results.vo

import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("lat")
    val lat: String,
    @SerializedName("long")
    val long: String,
    @SerializedName("locality")
    val locality: String,
    @SerializedName("country")
    val country: String
)