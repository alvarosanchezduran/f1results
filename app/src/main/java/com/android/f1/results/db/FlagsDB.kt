package com.android.f1.results.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.android.f1.results.db.converters.FlagContentConverter
import com.android.f1.results.vo.CountryResponse
import com.android.f1.results.vo.FlagsContent

@Database(
    entities = [CountryResponse::class, FlagsContent::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(FlagContentConverter::class)
abstract class FlagsDB : RoomDatabase() {

    abstract fun flagsDao(): FlagsDao

}