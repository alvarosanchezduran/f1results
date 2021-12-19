package com.android.f1.results.api

import androidx.lifecycle.LiveData
import com.android.f1.results.vo.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CircuitServiceApi {
    @GET("{year}/circuits.json")
    fun getCircuits(
        @Path("year") year: String,
        @Query("limit") limit: Int = 500
    ): LiveData<ApiResponse<F1Response<CircuitsTableResponse>>>

    @GET("circuits/{circuitId}/results/1.json")
    fun getCircuitTotalGP(
        @Path("circuitId") circuitId: String,
        @Query("limit") limit: Int = 500
    ): LiveData<ApiResponse<F1Response<RaceTableResponse>>>
}