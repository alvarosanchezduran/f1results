package com.android.f1.results.vo

import com.google.gson.annotations.SerializedName

data class ConstructorStanding(
    @SerializedName("position")
    val position: String,
    @SerializedName("positionText")
    val positionText: String,
    @SerializedName("points")
    val points: String,
    @SerializedName("wins")
    val wins: String,
    @SerializedName("Constructor")
    val constructor: Constructor
)