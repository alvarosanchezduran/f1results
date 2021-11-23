package com.android.f1.results.vo

import com.google.gson.annotations.SerializedName

data class DriverStanding(
    @SerializedName("position")
    val position: String,
    @SerializedName("positionText")
    val positionText: String,
    @SerializedName("points")
    val points: String,
    @SerializedName("wins")
    val wins: String,
    @SerializedName("Driver")
    val driver: Driver,
    @SerializedName("Constructors")
    val constructors: List<Constructor>,
    var selected: Boolean? = false,
    var leaderGap: String?,
    var teamMateGap: String?
) {
    fun calculateGap(firstPositionPoints: String) {
        val gap = firstPositionPoints.toFloat() - points.toFloat()
        if(gap != 0f) {
            var gapDecimals = gap.toString().split(".")
            if(gapDecimals[1].toInt() == 0) leaderGap = gapDecimals[0]
            else leaderGap = gap.toString()
        }
    }

    fun calculateTeammateGap(standing: List<DriverStanding>) {
        val constructorId = constructors.last().constructorId
        standing.forEach {
            if(it.constructors.last().constructorId == constructorId) {
                val gap = points.toFloat() - it.points.toFloat()
                if(gap < 0 && gap < teamMateGap?.toFloat()?: 0f) {
                    var gapDecimals = gap.toString().split(".")
                    if(gapDecimals[1].toInt() == 0) teamMateGap = gapDecimals[0]
                    else teamMateGap = gap.toString()
                }
            }
        }
    }

    fun getConstructorName(): String {
        return constructors.last().name
    }
}