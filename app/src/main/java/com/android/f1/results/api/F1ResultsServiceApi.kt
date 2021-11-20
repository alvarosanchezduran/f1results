package com.android.f1.results.api

import androidx.lifecycle.LiveData
import com.android.f1.results.vo.F1Response
import com.android.f1.results.vo.RaceTable
import com.android.f1.results.vo.RaceTableResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface F1ResultsServiceApi {
    @GET("{year}/{round}.json")
    fun getRace(
        @Path("year") year: String,
        @Path("round") round: String
    ): LiveData<ApiResponse<F1Response<RaceTableResponse>>>

    @GET("current/last/results.json")
    fun getLastRaceResult(): LiveData<ApiResponse<F1Response<RaceTableResponse>>>
}
