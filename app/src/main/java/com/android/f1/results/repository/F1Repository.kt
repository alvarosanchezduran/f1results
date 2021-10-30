package com.android.f1.results.repository

import androidx.lifecycle.LiveData
import com.android.f1.results.AppExecutors
import com.android.f1.results.api.F1ResultsServiceApi
import com.android.f1.results.testing.OpenForTesting
import com.android.f1.results.vo.F1Response
import com.android.f1.results.vo.RaceTable
import com.android.f1.results.vo.RaceTableResponse
import com.android.f1.results.vo.Resource
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository that handles Repo instances.
 *
 * unfortunate naming :/ .
 * Repo - value object name
 * Repository - type of this class.
 */
@Singleton
@OpenForTesting
class F1Repository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val f1ResultsServiceApi: F1ResultsServiceApi
) {
    fun example(): LiveData<Resource<F1Response<RaceTableResponse>>> {
        return object : NetworkBoundResource<F1Response<RaceTableResponse>, F1Response<RaceTableResponse>>(appExecutors) {
            override fun saveCallResult(item: F1Response<RaceTableResponse>) {}

            override fun shouldFetch(data: F1Response<RaceTableResponse>?): Boolean {
                return true
            }

            override fun loadFromDb() = null

            override fun createCall() = f1ResultsServiceApi.getRace()

            override fun onFetchFailed() { }
        }.asLiveData()
    }
}
