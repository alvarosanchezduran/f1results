package com.android.f1.results.api

import androidx.lifecycle.LiveData
import com.android.f1.results.vo.DriversTableResponse
import com.android.f1.results.vo.F1Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DriversServiceApi {
    @GET("{year}/drivers.json")
    fun getDrivers(
        @Path("year") year: String,
        @Query("limit") limit: Int = 500
    ): LiveData<ApiResponse<F1Response<DriversTableResponse>>>

    @GET("{year}/drivers/{driverId}/constructors.json")
    fun getDriverConstructorsForAYear(
        @Path("year") year: String,
        @Path("driverId") driverId: String
    ): LiveData<ApiResponse<F1Response<DriversTableResponse>>>
}