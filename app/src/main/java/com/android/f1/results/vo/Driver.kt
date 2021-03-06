package com.android.f1.results.vo

import com.android.f1.results.R
import com.android.f1.results.util.DriversImages
import com.google.gson.annotations.SerializedName

data class Driver(
    @SerializedName("driverId")
    val driverId: String,
    @SerializedName("permanentNumber")
    val permanentNumber: String?,
    @SerializedName("code")
    val code: String?,
    @SerializedName("url")
    val url: String,
    @SerializedName("givenName")
    val givenName: String,
    @SerializedName("familyName")
    val familyName: String,
    @SerializedName("dateOfBirth")
    val dateOfBirth: String,
    @SerializedName("nationality")
    val nationality: String,
    var totalGP: Int = 0
) {
    fun getImage(): Int {
        DriversImages.DRIVERS_IMAGES.get(driverId)?.let {
            return it
        }?: run {
            return R.drawable.ic_silueta2
        }
    }

    fun getCodeOrSurname(): String {
        return if(code != null) code else familyName.substring(0, 3).toUpperCase()
    }

    fun getName(): String {
        return givenName + " " + familyName
    }
}
