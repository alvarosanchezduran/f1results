package com.android.f1.results.vo

import com.google.gson.annotations.SerializedName

data class RaceTable(
        @SerializedName("season")
        val season: String,
        @SerializedName("round")
        val round: String,
        @SerializedName("Races")
        val races: List<Race>
)