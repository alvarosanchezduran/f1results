package com.android.f1.results.vo

import com.google.gson.annotations.SerializedName

data class CountryResponse(
    @SerializedName("flags")
    val flags: FlagsContent,
    @SerializedName("name")
    val name: CountryName
)

data class FlagsContent(
    @SerializedName("png")
    val png: String,
    @SerializedName("svg")
    val svg: String
)

data class CountryName (
    @SerializedName("common")
    val common: String
)