package com.android.f1.results.vo

import com.google.gson.annotations.SerializedName

data class F1Response<T: GenericResponse>(
        @SerializedName("MRData")
        val data: T
)
