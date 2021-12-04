package com.android.f1.results.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.android.example.github.util.InstantAppExecutors
import com.android.f1.results.api.ApiResponse
import com.android.f1.results.api.ResultServiceApi
import com.android.f1.results.api.StandingsServiceApi
import com.android.f1.results.vo.F1Response
import com.android.f1.results.vo.RaceTableResponse
import com.android.f1.results.vo.StandingsTableResponse
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

@RunWith(JUnit4::class)
class ResultsRespositoryTest {
    private val service = Mockito.mock(ResultServiceApi::class.java)
    private val repo = ResultRepository(InstantAppExecutors(), service)

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun goToNetworkLastResults() {
        val updatedDbData = MutableLiveData<ApiResponse<F1Response<RaceTableResponse>>>()
        Mockito.`when`(service.getLastResults("2021")).thenReturn(updatedDbData)
        repo.getLastResults("2021")
        Mockito.verify(service).getLastResults("2021")
    }

    @Test
    fun goToNetworkResult() {
        val updatedDbData = MutableLiveData<ApiResponse<F1Response<RaceTableResponse>>>()
        Mockito.`when`(service.getResult("2021", "20")).thenReturn(updatedDbData)
        repo.getQualifying("2021", "20")
        Mockito.verify(service).getResult("2021", "20")
    }
}