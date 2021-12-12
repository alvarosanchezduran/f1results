package com.android.f1.results.vo

import com.google.gson.annotations.SerializedName

data class Constructor(
    @SerializedName("constructorId")
    val constructorId: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("nationality")
    val nationality: String,

    var years: Int = 0,
    var carreras: Int = 0
)
