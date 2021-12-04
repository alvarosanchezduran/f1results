package com.android.f1.results.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.android.example.github.util.InstantAppExecutors
import com.android.f1.results.api.ApiResponse
import com.android.f1.results.api.DriversServiceApi
import com.android.f1.results.api.FlagServiceApi
import com.android.f1.results.db.FlagsDao
import com.android.f1.results.vo.CountryResponse
import com.android.f1.results.vo.DriversTableResponse
import com.android.f1.results.vo.F1Response
import com.android.f1.results.vo.FlagsContent
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.Mockito.never

@RunWith(JUnit4::class)
class FlagRepositoryTest {
    private val dao = Mockito.mock(FlagsDao::class.java)
    private val service = Mockito.mock(FlagServiceApi::class.java)
    private val repo = FlagRepository(InstantAppExecutors(), service, dao)

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun goToNetworkFlags() {
        val updatedDbData = MutableLiveData<ApiResponse<List<CountryResponse>>>()
        Mockito.`when`(service.getFlag("spain")).thenReturn(updatedDbData)
        repo.getFlag("spain")
        Mockito.verify(service).getFlag("spain")
    }

    @Test
    fun goToLocalFlags() {
        val updatedData = MutableLiveData<ApiResponse<List<CountryResponse>>>()
        val updatedDbData = MutableLiveData(listOf(CountryResponse(
            FlagsContent(), "")))
        Mockito.`when`(service.getFlag("spain")).thenReturn(updatedData)
        Mockito.`when`(dao.findFlagByCountryName("spain")).thenReturn(updatedDbData)
        repo.getFlag("spain")
        Mockito.verify(service, never()).getFlag("spain")
    }
}