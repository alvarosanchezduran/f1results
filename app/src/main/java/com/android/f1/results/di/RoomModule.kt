package com.android.f1.results.di

import android.app.Application
import androidx.room.Room
import com.android.f1.results.db.FlagsDB
import com.android.f1.results.db.FlagsDao
import dagger.Module
import dagger.Provides

@Module
internal class RoomModule {

    @Provides
    fun provideDb(app: Application): FlagsDB {
        return Room.databaseBuilder(app, FlagsDB::class.java, "flags.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideFlagsDao(db: FlagsDB): FlagsDao {
        return db.flagsDao()
    }

}