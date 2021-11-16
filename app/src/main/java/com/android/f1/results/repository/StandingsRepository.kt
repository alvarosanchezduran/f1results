package com.android.f1.results.repository

import androidx.lifecycle.LiveData
import com.android.f1.results.AppExecutors
import com.android.f1.results.api.ResultServiceApi
import com.android.f1.results.api.StandingsServiceApi
import com.android.f1.results.testing.OpenForTesting
import com.android.f1.results.vo.F1Response
import com.android.f1.results.vo.RaceTableResponse
import com.android.f1.results.vo.Resource
import com.android.f1.results.vo.StandingsTableResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@OpenForTesting
class StandingsRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val standingsServiceApi: StandingsServiceApi
) {

    fun getDriverStanding(year: String): LiveData<Resource<F1Response<StandingsTableResponse>>> {
        return object : NetworkBoundResource<F1Response<StandingsTableResponse>, F1Response<StandingsTableResponse>>(appExecutors) {
            override fun saveCallResult(items: F1Response<StandingsTableResponse>) {}

            override fun shouldFetch(data: F1Response<StandingsTableResponse>?): Boolean {
                return true
            }

            override fun loadFromDb() = null

            override fun createCall() = standingsServiceApi.getDriverStanding(year)

            override fun onFetchFailed() { }
        }.asLiveData()
    }

    fun getConstructorsStanding(year: String): LiveData<Resource<F1Response<StandingsTableResponse>>> {
        return object : NetworkBoundResource<F1Response<StandingsTableResponse>, F1Response<StandingsTableResponse>>(appExecutors) {
            override fun saveCallResult(items: F1Response<StandingsTableResponse>) {}

            override fun shouldFetch(data: F1Response<StandingsTableResponse>?): Boolean {
                return true
            }

            override fun loadFromDb() = null

            override fun createCall() = standingsServiceApi.getConstructorStanding(year)

            override fun onFetchFailed() { }
        }.asLiveData()
    }
}