package com.android.f1.results.vo

data class QualifyingRow(
    val firstPosition: DriverQualifying,
    val secondPosition: DriverQualifying?,
    var selected: Boolean = false
)
