package com.android.f1.results.api

import androidx.lifecycle.LiveData
import com.android.f1.results.vo.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ConstructorServiceApi {
    @GET("{year}/constructors.json")
    fun getConstructors(
        @Path("year") year: String,
        @Query("limit") limit: Int = 500
    ): LiveData<ApiResponse<F1Response<ConstructorsTableResponse>>>

    @GET("constructors/{constructorId}/drivers.json?limit=500")
    fun getConstructorsDrivers(
        @Path("constructorId") constructorId: String,
        @Query("limit") limit: Int = 500
    ): LiveData<ApiResponse<F1Response<DriversTableResponse>>>

    @GET("constructors/{constructorId}/results/1.json?limit=500")
    fun getConstructorGPWinned(
        @Path("constructorId") constructorId: String,
        @Query("limit") limit: Int = 500
    ): LiveData<ApiResponse<F1Response<TotalResponse>>>

    @GET("constructors/{constructorId}/constructorStandings/1.json?limit=500")
    fun getConstructorChampionships(
        @Path("constructorId") constructorId: String,
        @Query("limit") limit: Int = 500
    ): LiveData<ApiResponse<F1Response<TotalResponse>>>
}