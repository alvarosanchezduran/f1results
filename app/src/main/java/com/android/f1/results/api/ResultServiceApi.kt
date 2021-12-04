package com.android.f1.results.api

import androidx.lifecycle.LiveData
import com.android.f1.results.util.Constants.Companion.CURRENT_YEAR
import com.android.f1.results.vo.F1Response
import com.android.f1.results.vo.RaceTableResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ResultServiceApi {
    @GET("{year}/{round}/qualifying.json")
    fun getResult(
        @Path("year") year: String,
        @Path("round") round: String
    ): LiveData<ApiResponse<F1Response<RaceTableResponse>>>

    @GET("{year}/results.json")
    fun getLastResults(
        @Path("year") year: String = CURRENT_YEAR,
        @Query("limit") limit: Int = 500
    ): LiveData<ApiResponse<F1Response<RaceTableResponse>>>
}