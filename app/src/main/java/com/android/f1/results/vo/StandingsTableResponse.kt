package com.android.f1.results.vo

import com.google.gson.annotations.SerializedName

data class StandingsTableResponse(
    @SerializedName("StandingsTable")
    val standingsTable: StandingsTable
): GenericResponse