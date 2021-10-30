package com.android.f1.results.vo

import com.google.gson.annotations.SerializedName

data class RaceTableResponse(
    @SerializedName("RaceTable")
    val raceTable: RaceTable
): GenericResponse