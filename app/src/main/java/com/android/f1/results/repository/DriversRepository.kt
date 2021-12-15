package com.android.f1.results.repository

import androidx.lifecycle.LiveData
import com.android.f1.results.AppExecutors
import com.android.f1.results.api.DriversServiceApi
import com.android.f1.results.testing.OpenForTesting
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

    fun getDriverGP(driverId: String): LiveData<Resource<F1Response<RaceTableResponse>>> {
        return object : NetworkBoundResource<F1Response<RaceTableResponse>, F1Response<RaceTableResponse>>(appExecutors) {
            override fun saveCallResult(items: F1Response<RaceTableResponse>) {}

            override fun shouldFetch(data: F1Response<RaceTableResponse>?): Boolean {
                return true
            }

            override fun loadFromDb() = null

            override fun createCall() = driversServiceApi.getDriverGP(driverId)

            override fun onFetchFailed() { }
        }.asLiveData()
    }

    fun getDriverGPWinned(driverId: String): LiveData<Resource<F1Response<TotalResponse>>> {
        return object : NetworkBoundResource<F1Response<TotalResponse>, F1Response<TotalResponse>>(appExecutors) {
            override fun saveCallResult(items: F1Response<TotalResponse>) {}

            override fun shouldFetch(data: F1Response<TotalResponse>?): Boolean {
                return true
            }

            override fun loadFromDb() = null

            override fun createCall() = driversServiceApi.getDriverGPWinned(driverId)

            override fun onFetchFailed() { }
        }.asLiveData()
    }

    fun getDriverChampionships(driverId: String): LiveData<Resource<F1Response<TotalResponse>>> {
        return object : NetworkBoundResource<F1Response<TotalResponse>, F1Response<TotalResponse>>(appExecutors) {
            override fun saveCallResult(items: F1Response<TotalResponse>) {}

            override fun shouldFetch(data: F1Response<TotalResponse>?): Boolean {
                return true
            }

            override fun loadFromDb() = null

            override fun createCall() = driversServiceApi.getDriverChampionships(driverId)

            override fun onFetchFailed() { }
        }.asLiveData()
    }
}