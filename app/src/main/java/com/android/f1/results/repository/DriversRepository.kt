package com.android.f1.results.repository

import androidx.lifecycle.LiveData
import com.android.f1.results.AppExecutors
import com.android.f1.results.api.DriversServiceApi
import com.android.f1.results.api.FlagServiceApi
import com.android.f1.results.api.ResultServiceApi
import com.android.f1.results.db.FlagsDao
import com.android.f1.results.testing.OpenForTesting
import com.android.f1.results.util.Constants.Companion.CURRENT_YEAR
import com.android.f1.results.vo.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@OpenForTesting
class DriversRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val driversServiceApi: DriversServiceApi
) {
    fun getDrivers(year: String): LiveData<Resource<F1Response<DriversTableResponse>>> {
        return object : NetworkBoundResource<F1Response<DriversTableResponse>, F1Response<DriversTableResponse>>(appExecutors) {
            override fun saveCallResult(items: F1Response<DriversTableResponse>) {}

            override fun shouldFetch(data: F1Response<DriversTableResponse>?): Boolean {
                return true
            }

            override fun loadFromDb() = null

            override fun createCall() = driversServiceApi.getDrivers(year)

            override fun onFetchFailed() { }
        }.asLiveData()
    }
}