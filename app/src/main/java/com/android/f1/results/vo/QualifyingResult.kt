package com.android.f1.results.vo

import com.google.gson.annotations.SerializedName

data class QualifyingResult(
    @SerializedName("number")
    val number: String,
    @SerializedName("position")
    val position: String,
    @SerializedName("Driver")
    val driver: Driver,
    @SerializedName("Constructor")
    val constructor: Constructor,
    @SerializedName("Q1")
    val q1: String?,
    @SerializedName("Q2")
    val q2: String?,
    @SerializedName("Q3")
    val q3: String?,
) {
    fun getQualificationTime(): String {
        if (q3 != null) return q3
        else if(q2 != null) return q2
        else if(q1 != null) return q1
        else return ""
    }
}