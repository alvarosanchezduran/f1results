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
    val constructor: Constructor,
    var selected: Boolean? = false,
    var leaderGap: String?
){
    fun calculateGap(firstPositionPoints: String) {
        val gap = firstPositionPoints.toFloat() - points.toFloat()
        if(gap != 0f) {
            var gapDecimals = gap.toString().split(".")
            if(gapDecimals[1].toInt() == 0) leaderGap = gapDecimals[0]
            else leaderGap = gap.toString()
        }
    }
}