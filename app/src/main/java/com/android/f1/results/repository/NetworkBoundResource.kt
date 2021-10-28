package com.android.f1.results.repository

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.android.f1.results.AppExecutors
import com.android.f1.results.api.ApiEmptyResponse
import com.android.f1.results.api.ApiErrorResponse
import com.android.f1.results.api.ApiResponse
import com.android.f1.results.api.ApiSuccessResponse
import com.android.f1.results.vo.Resource

/**
 * A generic class that can provide a resource backed by both the sqlite database and the network.
 *
 *
 * You can read more about it in the [Architecture
 * Guide](https://developer.android.com/arch).
 * @param <ResultType>
 * @param <RequestType>
</RequestType></ResultType> */
abstract class NetworkBoundResource<ResultType, RequestType>
@MainThread constructor(
        private val appExecutors: AppExecutors
) {

    companion object {
        private const val TAG = "NetworkBoundResource"
    }

    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        result.value = Resource.loading(null)
        @Suppress("LeakingThis")
        val dbSource: LiveData<ResultType>? = loadFromDb()

        dbSource?.let {
            result.addSource(dbSource) { data ->
                result.removeSource(dbSource)
                if (shouldFetch(data)) {
                    fetchFromNetwork(dbSource)
                } else {
                    result.addSource(dbSource) { newData ->
                        setValue(Resource.success(newData))
                    }
                }
            }
        } ?: run {
            fetchFromNetwork(null)
        }

    }

    @MainThread
    private fun setValue(newValue: Resource<ResultType>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>?) {
        val apiResponse = createCall()

        dbSource?.let {
            result.addSource(dbSource) { newData ->
                setValue(Resource.loading(newData))
            }
        }

        apiResponse?.let {
            result.addSource(it) { response ->
                result.removeSource(apiResponse)

                dbSource?.let {
                    result.removeSource(dbSource)
                }

                when (response) {
                    is ApiSuccessResponse -> {
                        appExecutors.diskIO().execute {
                            val responseBody = processResponse(response)
                            if (isDataFromNetworkCorrect(responseBody)) {
                                saveCallResult(responseBody)
                                appExecutors.mainThread().execute {
                                    // we specially request a new live data,
                                    // otherwise we will get immediately last cached value,
                                    // which may not be updated with latest results received from network.
                                    dbSource?.let {
                                        result.addSource(dbSource) { newData ->
                                            setValue(Resource.success(newData))
                                        }
                                    } ?: run {
                                        appExecutors.mainThread().execute {
                                            setValue(Resource.success(responseBody) as Resource<ResultType>)
                                        }
                                    }
                                }
                            } else {
                                onFetchFailed()

                                dbSource?.let {
                                    result.addSource(dbSource) { newData ->
                                        setValue(Resource.errorInvalidData(newData))
                                    }
                                } ?: run {
                                    appExecutors.mainThread().execute {
                                        setValue(Resource.errorInvalidData(responseBody) as Resource<ResultType>)
                                    }
                                }
                            }
                        }
                    }
                    is ApiEmptyResponse -> {
                        appExecutors.mainThread().execute {
                            dbSource?.let {
                                result.addSource(dbSource) { newData ->
                                    setValue(Resource.success(newData))
                                }
                            } ?: run {
                                setValue(Resource.success(null))
                            }
                        }
                    }
                    is ApiErrorResponse -> {
                        onFetchFailed()
                        appExecutors.mainThread().execute {
                            dbSource?.let {
                                result.addSource(dbSource) { newData ->
                                    setValue(Resource.error(response.errorMessage, newData))
                                }
                            } ?: run {
                                setValue(Resource.error(response.errorMessage, null))
                            }
                        }
                    }
                }
            }
        }
    }

    protected open fun onFetchFailed() {}

    fun asLiveData() = result as LiveData<Resource<ResultType>>

    @WorkerThread
    protected open fun processResponse(response: ApiSuccessResponse<RequestType>) = response.body

    @WorkerThread
    protected abstract fun saveCallResult(response: RequestType)

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    protected abstract fun loadFromDb(): LiveData<ResultType>?

    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>?

    @WorkerThread
    protected open fun isDataFromNetworkCorrect(data: RequestType?): Boolean {
        return true
    }

}