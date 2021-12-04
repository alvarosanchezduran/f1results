package com.android.f1.results.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.android.example.github.util.InstantAppExecutors
import com.android.f1.results.api.ApiResponse
import com.android.f1.results.api.DriversServiceApi
import com.android.f1.results.api.ResultServiceApi
import com.android.f1.results.vo.DriversTableResponse
import com.android.f1.results.vo.F1Response
import com.android.f1.results.vo.RaceTableResponse
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

@RunWith(JUnit4::class)
class DriversRepositoryTest {
    private val service = Mockito.mock(DriversServiceApi::class.java)
    private val repo = DriversRepository(InstantAppExecutors(), service)

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun goToNetworkLastResults() {
        val updatedDbData = MutableLiveData<ApiResponse<F1Response<DriversTableResponse>>>()
        Mockito.`when`(service.getDrivers("2021")).thenReturn(updatedDbData)
        repo.getDrivers("2021")
        Mockito.verify(service).getDrivers("2021")
    }
}