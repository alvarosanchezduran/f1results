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
    val results: List<Result>?,
    var flag: String?
) {
    fun getDateZoned(): String {
        val format = "dd MMM"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val formatter = DateTimeFormatter.ofPattern(format)
            getCorrectZoneTime()?.let {
                return formatter.format(it).replace(".", "").toUpperCase()
            }
        } else {
            getCorrectTime()?.let {
                return SimpleDateFormat(format).format(it).replace(".", "").toUpperCase()
            }
        }
        return ""
    }

    fun getTimeZoned(): String {
        val format = "HH:mm"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val formatter = DateTimeFormatter.ofPattern("HH:mm")
            getCorrectZoneTime()?.let {
                return formatter.format(it)
            }
        } else {
            getCorrectTime()?.let {
                return SimpleDateFormat(format).format(it)
            }
        }

        return ""
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCorrectZoneTime(): ZonedDateTime? {
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

    private fun getCorrectTime(): Date? {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-ddHH:mm:ss'Z'")
        return simpleDateFormat.parse((date + time))
    }

    fun getWinner(): Driver? {
        return results?.get(0)?.driver
    }
}