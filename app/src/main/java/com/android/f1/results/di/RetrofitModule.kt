package com.android.f1.results.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.f1.results.BuildConfig
import com.android.f1.results.api.F1ResultsServiceApi
import com.android.f1.results.ui.home.HomeViewModel
import com.android.f1.results.util.LiveDataCallAdapterFactory
import com.android.f1.results.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class RetrofitModule {

    @Singleton
    @Provides
    fun provideF1ResultsService(): F1ResultsServiceApi {
        return getRetrofitAdapter().create(F1ResultsServiceApi::class.java)
    }

    private fun getRetrofitAdapter(): Retrofit {
        val client = OkHttpClient.Builder()
                .callTimeout(BuildConfig.TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(BuildConfig.TIMEOUT, TimeUnit.MILLISECONDS)

        return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .client(client.build())
                .build()
    }

}