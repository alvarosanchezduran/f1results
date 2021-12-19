package com.android.f1.results.vo

import com.google.gson.annotations.SerializedName

data class CircuitsTableResponse(
    @SerializedName("CircuitTable")
    val circuitTable: CircuitTable
): GenericResponse