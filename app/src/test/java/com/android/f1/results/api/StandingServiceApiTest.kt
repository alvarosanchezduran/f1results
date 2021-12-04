package com.android.f1.results.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.android.f1.results.getOrAwaitValue
import com.android.f1.results.util.LiveDataCallAdapterFactory
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Okio
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.core.IsNull.notNullValue
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class StandingServiceApiTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var service: StandingsServiceApi

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
            .create(StandingsServiceApi::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun getDriverStanding() {
        enqueueResponse("driver-standing.json")
        val ds = (service.getDriverStanding("2021").getOrAwaitValue() as ApiSuccessResponse).body

        val request = mockWebServer.takeRequest()
        assertThat(request.path, `is`("/2021/driverStandings.json"))

        assertThat(ds, notNullValue())
        assertThat(ds.data.standingsTable.season, `is`("2021"))
        assertThat(ds.data.standingsTable.standingsLists[0].driverStanding.first().driver.driverId, `is`("max_verstappen"))
    }

    @Test
    fun getConstructorsStanding() {
        enqueueResponse("constructors-standing.json")
        val ds = (service.getConstructorStanding("2021").getOrAwaitValue() as ApiSuccessResponse).body

        val request = mockWebServer.takeRequest()
        assertThat(request.path, `is`("/2021/constructorStandings.json"))

        assertThat(ds, notNullValue())
        assertThat(ds.data.standingsTable.season, `is`("2021"))
        assertThat(ds.data.standingsTable.standingsLists[0].constructorStanding.first().constructor.constructorId, `is`("mercedes"))

    }

    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader!!
            .getResourceAsStream("api-response/$fileName")
        val source = Okio.buffer(Okio.source(inputStream))
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(
            mockResponse
                .setBody(source.readString(Charsets.UTF_8))
        )
    }
}