package com.android.f1.results.api

import androidx.lifecycle.LiveData
import com.android.f1.results.vo.TotalResponse
import com.android.f1.results.vo.DriversTableResponse
import com.android.f1.results.vo.F1Response
import com.android.f1.results.vo.RaceTableResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DriversServiceApi {
    @GET("{year}/drivers.json")
    fun getDrivers(
        @Path("year") year: String,
        @Query("limit") limit: Int = 100
    ): LiveData<ApiResponse<F1Response<DriversTableResponse>>>

    @GET("{year}/drivers/{driverId}/constructors.json")
    fun getDriverConstructorsForAYear(
        @Path("year") year: String,
        @Path("driverId") driverId: String
    ): LiveData<ApiResponse<F1Response<DriversTableResponse>>>

    @GET("drivers/{driverId}/results.json?limit=500")
    fun getDriverGP(
        @Path("driverId") driverId: String,
        @Query("limit") limit: Int = 500
    ): LiveData<ApiResponse<F1Response<RaceTableResponse>>>

    @GET("drivers/{driverId}/results/1.json?limit=500")
    fun getDriverGPWinned(
        @Path("driverId") driverId: String,
        @Query("limit") limit: Int = 500
    ): LiveData<ApiResponse<F1Response<TotalResponse>>>

    @GET("drivers/{driverId}/driverStandings/1.json?limit=500")
    fun getDriverChampionships(
        @Path("driverId") driverId: String,
        @Query("limit") limit: Int = 500
    ): LiveData<ApiResponse<F1Response<TotalResponse>>>


}