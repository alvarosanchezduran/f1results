package com.android.f1.results.vo

import com.google.gson.annotations.SerializedName

data class ConstructorTable(
    @SerializedName("season")
    val season: String,
    @SerializedName("Constructors")
    val constructors: List<Constructor>
)