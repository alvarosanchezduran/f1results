package com.android.f1.results.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.android.f1.results.vo.CountryResponse

@Dao
interface FlagsDao : BaseDao<CountryResponse> {

    @Query("SELECT * FROM CountryResponse WHERE countryName = :countryName")
    fun findFlagByCountryName(countryName: String): LiveData<List<CountryResponse>>

    @Query("DELETE FROM CountryResponse")
    fun removeAll(): Unit

}