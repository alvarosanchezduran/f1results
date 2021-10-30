package com.android.f1.results.repository

import androidx.lifecycle.LiveData
import com.android.f1.results.AppExecutors
import com.android.f1.results.api.F1ResultsServiceApi
import com.android.f1.results.api.FlagServiceApi
import com.android.f1.results.testing.OpenForTesting
import com.android.f1.results.vo.CountryResponse
import com.android.f1.results.vo.F1Response
import com.android.f1.results.vo.RaceTableResponse
import com.android.f1.results.vo.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@OpenForTesting
class FlagRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val flagServiceApi: FlagServiceApi
) {
    fun getFlag(country: String): LiveData<Resource<List<CountryResponse>>> {
        return object : NetworkBoundResource<List<CountryResponse>, List<CountryResponse>>(appExecutors) {
            override fun saveCallResult(item: List<CountryResponse>) {}

            override fun shouldFetch(data: List<CountryResponse>?): Boolean {
                return true
            }

            override fun loadFromDb() = null

            override fun createCall() = flagServiceApi.getFlag(country)

            override fun onFetchFailed() { }
        }.asLiveData()
    }
}