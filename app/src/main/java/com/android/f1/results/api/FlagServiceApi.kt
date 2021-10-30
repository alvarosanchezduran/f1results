package com.android.f1.results.api

import androidx.lifecycle.LiveData
import com.android.f1.results.vo.CountryResponse
import com.android.f1.results.vo.F1Response
import com.android.f1.results.vo.RaceTableResponse
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Path

interface FlagServiceApi {
    @GET("name/{country}")
    fun getFlag(@Path("country") country: String): LiveData<ApiResponse<List<CountryResponse>>>
}
