package com.android.f1.results.vo

import androidx.room.Entity
import androidx.room.TypeConverters
import com.android.f1.results.db.converters.FlagContentConverter
import com.google.gson.annotations.SerializedName

@Entity(
    primaryKeys = ["countryName"]
)
data class CountryResponse(
    @SerializedName("flags")
    @TypeConverters(FlagContentConverter::class)
    val flags: FlagsContent,
    var countryName: String = ""
)

@Entity(
    primaryKeys = ["png"]
)
data class FlagsContent(
    @SerializedName("png")
    val png: String,
    @SerializedName("svg")
    val svg: String
) {
    constructor() : this("", "")
}