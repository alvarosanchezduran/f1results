package com.android.f1.results.api

import androidx.lifecycle.LiveData
import com.android.f1.results.vo.F1Response
import com.android.f1.results.vo.StandingsTableResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface StandingsServiceApi {

    @GET("{year}/driverStandings.json")
    fun getDriverStanding(
        @Path("year") year: String
    ): LiveData<ApiResponse<F1Response<StandingsTableResponse>>>

    @GET("{year}/constructorStandings.json")
    fun getConstructorStanding(
        @Path("year") year: String
    ): LiveData<ApiResponse<F1Response<StandingsTableResponse>>>

}