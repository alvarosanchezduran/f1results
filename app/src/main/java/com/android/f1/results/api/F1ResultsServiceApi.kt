package com.android.f1.results.api

import androidx.lifecycle.LiveData
import com.android.f1.results.vo.F1Response
import com.android.f1.results.vo.RaceTable
import com.android.f1.results.vo.RaceTableResponse
import retrofit2.http.GET

/**
 * REST API access points
 */
interface F1ResultsServiceApi {
    @GET("api/f1/2021/18.json")
    fun getRace(): LiveData<ApiResponse<F1Response<RaceTableResponse>>>
}
