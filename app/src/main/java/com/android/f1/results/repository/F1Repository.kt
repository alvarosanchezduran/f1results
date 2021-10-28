package com.android.f1.results.repository

import androidx.lifecycle.LiveData
import com.android.f1.results.AppExecutors
import com.android.f1.results.api.F1Results
import com.android.f1.results.testing.OpenForTesting
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
    private val f1Results: F1Results
) {
    fun example(): LiveData<Resource<Any>> {
        return object : NetworkBoundResource<Any, Any>(appExecutors) {
            override fun saveCallResult(item: Any) {}

            override fun shouldFetch(data: Any?): Boolean {
                return true
            }

            override fun loadFromDb() = null

            override fun createCall() = f1Results.getUser()

            override fun onFetchFailed() { }
        }.asLiveData()
    }
}
