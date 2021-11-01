package com.android.f1.results.db.converters

import androidx.room.TypeConverter
import com.android.f1.results.vo.FlagsContent
import com.google.gson.Gson

open class FlagContentConverter {

    @TypeConverter
    fun fromString(value: String?): FlagsContent? {
        return value?.let { valueStr ->
            Gson().fromJson(valueStr, FlagsContent().javaClass)
        }
    }

    @TypeConverter
    fun toString(item: FlagsContent?): String? {
        return item?.let {
            Gson().toJson(item)
        }
    }

}