package com.android.f1.results.di

import android.app.Application
import android.content.Context
import android.content.res.AssetManager
import android.content.res.Resources
import com.android.f1.results.api.F1ResultsServiceApi
import com.android.f1.results.db.preferences.F1ResultsPreferences
import com.android.f1.results.util.LiveDataCallAdapterFactory
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {

    @Provides
    fun provideContext(app: Application): Context {
        return app.baseContext
    }

    @Provides
    fun providePreferences(app: Context): F1ResultsPreferences {
        return F1ResultsPreferences(app)
    }

    @Provides
    fun provideFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }
}
