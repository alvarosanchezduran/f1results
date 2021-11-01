package com.android.f1.results.repository

import androidx.lifecycle.LiveData
import com.android.f1.results.AppExecutors
import com.android.f1.results.api.F1ResultsServiceApi
import com.android.f1.results.api.FlagServiceApi
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
class FlagRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val flagServiceApi: FlagServiceApi,
    private val flagsDao: FlagsDao,
) {
    fun getFlag(country: String): LiveData<Resource<List<CountryResponse>>> {
        return object : NetworkBoundResource<List<CountryResponse>, List<CountryResponse>>(appExecutors) {
            override fun saveCallResult(items: List<CountryResponse>) {
                var item = items.get(0)
                item.countryName = country
                flagsDao.insert(item)
            }

            override fun shouldFetch(data: List<CountryResponse>?): Boolean {
                return data == null || data.size == 0
            }

            override fun loadFromDb() = flagsDao.findFlagByCountryName(country)

            override fun createCall() = flagServiceApi.getFlag(country)

            override fun onFetchFailed() { }
        }.asLiveData()
    }
}