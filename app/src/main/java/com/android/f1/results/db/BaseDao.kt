package com.android.f1.results.db

import androidx.room.Insert
import androidx.room.OnConflictStrategy

interface BaseDao<Input> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(template: Input)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItems(data: List<Input>)

}