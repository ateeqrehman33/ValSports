package com.attitude.designs.valtrackr.di

import android.app.Application
import android.content.Context
import com.attitude.designs.valtrackr.api.ValoApiService
import com.attitude.designs.valtrackr.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideBaseUrl() = Constants.BASE_URL

    private val okHttpClient = OkHttpClient().newBuilder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        // Attempting to add headers to every request
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("x-api-key", "0TvQnueqKa5mxJntVWt0w4LpLfEkrV1Ta8rQBb9Z")
            chain.proceed(request.build())
        }
        .build()


    @Provides
    @Singleton
    fun provideRetrofitInstance(BASE_URL: String): ValoApiService =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ValoApiService::class.java)

    @Singleton
    @Provides
    fun provideContext(application: Application): Context = application.applicationContext


}