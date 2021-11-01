package com.android.f1.results.vo

import com.google.gson.annotations.SerializedName

data class CountryResponse(
    @SerializedName("flags")
    val flags: FlagsContent,
    var countryName: String?
)

data class FlagsContent(
    @SerializedName("png")
    val png: String,
    @SerializedName("svg")
    val svg: String
)