package com.android.f1.results.repository
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.android.example.github.util.InstantAppExecutors
import com.android.f1.results.api.ApiResponse
import com.android.f1.results.api.StandingsServiceApi
import com.android.f1.results.vo.F1Response
import com.android.f1.results.vo.StandingsTableResponse
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify

@RunWith(JUnit4::class)
class StandingRepositoryTest {
    private val standingService = mock(StandingsServiceApi::class.java)
    private val repo = StandingsRepository(InstantAppExecutors(), standingService)

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun goToNetworkDrivers() {
        val updatedDbData = MutableLiveData<ApiResponse<F1Response<StandingsTableResponse>>>()
        `when`(standingService.getDriverStanding("2021")).thenReturn(updatedDbData)
        repo.getDriverStanding("2021")
        verify(standingService).getDriverStanding("2021")
    }

    @Test
    fun goToNetworkConstructors() {
        val updatedDbData = MutableLiveData<ApiResponse<F1Response<StandingsTableResponse>>>()
        `when`(standingService.getConstructorStanding("2021")).thenReturn(updatedDbData)
        repo.getConstructorsStanding("2021")
        verify(standingService).getConstructorStanding("2021")
    }
}