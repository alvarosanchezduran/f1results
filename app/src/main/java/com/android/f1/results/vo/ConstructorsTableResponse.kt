package com.android.f1.results.vo

import com.google.gson.annotations.SerializedName

data class ConstructorsTableResponse(
    @SerializedName("ConstructorTable")
    val constructorTable: ConstructorTable
): GenericResponse
