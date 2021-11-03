package com.android.f1.results.repository

import androidx.lifecycle.LiveData
import com.android.f1.results.AppExecutors
import com.android.f1.results.api.FlagServiceApi
import com.android.f1.results.api.ResultServiceApi
import com.android.f1.results.db.FlagsDao
import com.android.f1.results.testing.OpenForTesting
import com.android.f1.results.vo.CountryResponse
import com.android.f1.results.vo.F1Response
import com.android.f1.results.vo.RaceTableResponse
import com.android.f1.results.vo.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@OpenForTesting
class ResultRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val resultServiceApi: ResultServiceApi
) {
    fun getQualifying(year: String, round: String): LiveData<Resource<F1Response<RaceTableResponse>>> {
        return object : NetworkBoundResource<F1Response<RaceTableResponse>, F1Response<RaceTableResponse>>(appExecutors) {
            override fun saveCallResult(items: F1Response<RaceTableResponse>) {}

            override fun shouldFetch(data: F1Response<RaceTableResponse>?): Boolean {
                return true
            }

            override fun loadFromDb() = null

            override fun createCall() = resultServiceApi.getQualifying(year, round)

            override fun onFetchFailed() { }
        }.asLiveData()
    }
}