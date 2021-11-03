package com.android.f1.results.api

import androidx.lifecycle.LiveData
import com.android.f1.results.vo.F1Response
import com.android.f1.results.vo.RaceTableResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ResultServiceApi {
    @GET("{year}/{round}/qualifying.json")
    fun getQualifying(
        @Path("year") year: String,
        @Path("round") round: String
    ): LiveData<ApiResponse<F1Response<RaceTableResponse>>>
}