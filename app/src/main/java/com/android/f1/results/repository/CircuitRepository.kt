package com.android.f1.results.repository

import androidx.lifecycle.LiveData
import com.android.f1.results.AppExecutors
import com.android.f1.results.api.CircuitServiceApi
import com.android.f1.results.testing.OpenForTesting
import com.android.f1.results.vo.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@OpenForTesting
class CircuitRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val circuitServiceApi: CircuitServiceApi
) {
    fun getCicuits(year: String): LiveData<Resource<F1Response<CircuitsTableResponse>>> {
        return object : NetworkBoundResource<F1Response<CircuitsTableResponse>, F1Response<CircuitsTableResponse>>(appExecutors) {
            override fun saveCallResult(items: F1Response<CircuitsTableResponse>) {}

            override fun shouldFetch(data: F1Response<CircuitsTableResponse>?): Boolean {
                return true
            }

            override fun loadFromDb() = null

            override fun createCall() = circuitServiceApi.getCircuits(year)

            override fun onFetchFailed() { }
        }.asLiveData()
    }

    fun getCircuitTotalGP(circuitId: String): LiveData<Resource<F1Response<RaceTableResponse>>> {
        return object : NetworkBoundResource<F1Response<RaceTableResponse>, F1Response<RaceTableResponse>>(appExecutors) {
            override fun saveCallResult(items: F1Response<RaceTableResponse>) {}

            override fun shouldFetch(data: F1Response<RaceTableResponse>?): Boolean {
                return true
            }

            override fun loadFromDb() = null

            override fun createCall() = circuitServiceApi.getCircuitTotalGP(circuitId)

            override fun onFetchFailed() { }
        }.asLiveData()
    }
}