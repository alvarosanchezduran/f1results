package com.android.f1.results.vo

import com.google.gson.annotations.SerializedName

data class DriversTableResponse(
    @SerializedName("DriverTable")
    val raceTable: DriverTable
): GenericResponse
