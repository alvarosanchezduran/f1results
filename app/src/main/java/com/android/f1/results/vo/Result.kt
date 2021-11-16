package com.android.f1.results.vo

import android.content.Context
import com.android.f1.results.util.FinishStatus
import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("number")
    val number: String? = "",
    @SerializedName("position")
    val position: String,
    @SerializedName("positionText")
    val positionText: String,
    @SerializedName("points")
    val points: String,
    @SerializedName("Driver")
    val driver: Driver,
    @SerializedName("Constructor")
    val constructor: Constructor,
    @SerializedName("grid")
    val grid: String,
    @SerializedName("laps")
    val laps: String?,
    @SerializedName("status")
    val status: String,
    @SerializedName("Time")
    val time: Time?,
    @SerializedName("FastestLap")
    val fastestLap: FastestLap?,
    var selected: Boolean? = false
) {
    fun getFinalTime(context: Context): String {
        time?.let {
            return it.time
        }?: run  {
            return getFinishingStatus(context)
        }
    }

    fun isFastesLap(): Boolean {
        fastestLap?.let {
            return it.rank == "1"
        }?: run  {
            return false
        }
    }

    fun getFinishingStatus(context: Context): String {
        enumValues<FinishStatus>().forEach {
            if(status == it.value) return context.resources.getString(it.text)
        }
        return status
    }
}
