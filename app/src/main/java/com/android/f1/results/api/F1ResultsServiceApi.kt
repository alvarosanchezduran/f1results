package com.android.f1.results.api

import androidx.lifecycle.LiveData
import retrofit2.http.GET

/**
 * REST API access points
 */
interface F1ResultsServiceApi {
    @GET("ejemplo")
    fun getUser(): LiveData<ApiResponse<Any>>
}
