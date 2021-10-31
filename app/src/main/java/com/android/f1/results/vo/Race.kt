package com.android.f1.results.vo

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

data class Race(
    @SerializedName("season")
    val season: String,
    @SerializedName("round")
    val round: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("raceName")
    val raceName: String,
    @SerializedName("Circuit")
    val circuit: Circuit,
    @SerializedName("date")
    val date: String,
    @SerializedName("time")
    val time: String,
    @SerializedName("Results")
    val results: List<Result>?
) {
    fun getDateZoned(): String {
        val formatter = DateTimeFormatter.ofPattern("dd MMM")
        getCorrectTime()?.let {
            return formatter.format(it).replace(".", "").toUpperCase()
        }
        return ""
    }

    fun getTimeZoned(): String {
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        getCorrectTime()?.let {
            return formatter.format(it)
        }
        return ""
    }

    fun getCorrectTime(): ZonedDateTime? {
        var timeZone = TimeZone.getDefault()
        val formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-ddHH:mm:ss'Z'").withZone(timeZone.toZoneId())
        val time = ZonedDateTime.parse((date + time), formatter)
        val formatterZone = DateTimeFormatter.ofPattern("Z")
        val zoned = formatterZone.format(time)
        val zonedArray = zoned.toCharArray()
        var finalZone = time
        if(zonedArray[0].toString() == "+") finalZone = time.plusHours(("" + zonedArray[1] + zonedArray[2]).toLong())
        else finalZone = time.minusHours(("" + zonedArray[1] + zonedArray[2]).toLong())

        return finalZone
    }
}