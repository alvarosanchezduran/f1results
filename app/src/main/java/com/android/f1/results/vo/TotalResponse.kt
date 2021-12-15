package com.android.f1.results.vo

import com.google.gson.annotations.SerializedName

data class TotalResponse(
    @SerializedName("total")
    val total: String
): GenericResponse
