package com.android.f1.results.repository

import androidx.lifecycle.LiveData
import com.android.f1.results.AppExecutors
import com.android.f1.results.api.ConstructorServiceApi
import com.android.f1.results.api.DriversServiceApi
import com.android.f1.results.testing.OpenForTesting
import com.android.f1.results.vo.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@OpenForTesting
class ConstructorsRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val constructorServiceApi: ConstructorServiceApi
) {
    fun getConstructors(year: String): LiveData<Resource<F1Response<ConstructorsTableResponse>>> {
        return object : NetworkBoundResource<F1Response<ConstructorsTableResponse>, F1Response<ConstructorsTableResponse>>(appExecutors) {
            override fun saveCallResult(items: F1Response<ConstructorsTableResponse>) {}

            override fun shouldFetch(data: F1Response<ConstructorsTableResponse>?): Boolean {
                return true
            }

            override fun loadFromDb() = null

            override fun createCall() = constructorServiceApi.getConstructors(year)

            override fun onFetchFailed() { }
        }.asLiveData()
    }

    fun getConstructorsDrivers(constructorId: String): LiveData<Resource<F1Response<DriversTableResponse>>> {
        return object : NetworkBoundResource<F1Response<DriversTableResponse>, F1Response<DriversTableResponse>>(appExecutors) {
            override fun saveCallResult(items: F1Response<DriversTableResponse>) {}

            override fun shouldFetch(data: F1Response<DriversTableResponse>?): Boolean {
                return true
            }

            override fun loadFromDb() = null

            override fun createCall() = constructorServiceApi.getConstructorsDrivers(constructorId)

            override fun onFetchFailed() { }
        }.asLiveData()
    }

    fun getConstructorsGPWinned(constructorId: String): LiveData<Resource<F1Response<TotalResponse>>> {
        return object : NetworkBoundResource<F1Response<TotalResponse>, F1Response<TotalResponse>>(appExecutors) {
            override fun saveCallResult(items: F1Response<TotalResponse>) {}

            override fun shouldFetch(data: F1Response<TotalResponse>?): Boolean {
                return true
            }

            override fun loadFromDb() = null

            override fun createCall() = constructorServiceApi.getConstructorGPWinned(constructorId)

            override fun onFetchFailed() { }
        }.asLiveData()
    }

    fun getConstructorsChampionshipsWinned(constructorId: String): LiveData<Resource<F1Response<TotalResponse>>> {
        return object : NetworkBoundResource<F1Response<TotalResponse>, F1Response<TotalResponse>>(appExecutors) {
            override fun saveCallResult(items: F1Response<TotalResponse>) {}

            override fun shouldFetch(data: F1Response<TotalResponse>?): Boolean {
                return true
            }

            override fun loadFromDb() = null

            override fun createCall() = constructorServiceApi.getConstructorChampionships(constructorId)

            override fun onFetchFailed() { }
        }.asLiveData()
    }
}