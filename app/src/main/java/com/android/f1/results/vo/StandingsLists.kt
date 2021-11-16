package com.android.f1.results.vo

import com.google.gson.annotations.SerializedName

data class StandingsLists(
    @SerializedName("season")
    val season: String,
    @SerializedName("round")
    val round: String,
    @SerializedName("ConstructorStandings")
    val constructorStanding: List<ConstructorStanding>,
    @SerializedName("DriverStandings")
    val driverStanding: List<DriverStanding>
)