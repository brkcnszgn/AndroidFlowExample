package com.example.androidflowexample.core.data.module

import android.content.Context
import com.example.androidflowexample.BuildConfig
import com.example.androidflowexample.core.data.ApiKeyAndLanguageInterceptor
import com.example.androidflowexample.core.data.di.DispatcherImpl
import com.example.androidflowexample.core.data.di.DispatcherProvider
import com.example.androidflowexample.core.data.network.api.ApiHelper
import com.example.androidflowexample.core.data.network.api.ApiHelperImpl
import com.example.androidflowexample.core.data.network.api.ApiService
import com.example.androidflowexample.core.utils.NetworkHelper
import com.example.androidflowexample.core.utils.ResourceProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ApplicationModule {
    @Binds
    abstract fun provideApiHelper(apiHelper: ApiHelperImpl): ApiHelper

    @Binds
    abstract fun provideDispatchers(dispatcherImpl: DispatcherImpl): DispatcherProvider

    companion object {
        @Provides
        @Named("BASE_URL")
        fun provideBaseUrl() = BuildConfig.BASE_URL

        @Provides
        @Singleton
        fun provideApiKeyInterceptor(): ApiKeyAndLanguageInterceptor =
            ApiKeyAndLanguageInterceptor()

        @Provides
        @Singleton
        fun provideOkHttpClient(
            apiKeyAndLanguageInterceptor: ApiKeyAndLanguageInterceptor,
            @ApplicationContext context: Context
        ) =
            if (BuildConfig.DEBUG) {
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

                OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .addInterceptor(apiKeyAndLanguageInterceptor)
                    .build()
            } else {
                OkHttpClient.Builder()
                    .addInterceptor(apiKeyAndLanguageInterceptor)
                    .build()
            }


        @Provides
        @Singleton
        fun provideRetrofit(
            okHttpClient: OkHttpClient,
            @Named("BASE_URL") baseUrl: String
        ): Retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build()

        @Provides
        @Singleton
        fun provideApiService(retrofit: Retrofit): ApiService =
            retrofit.create(ApiService::class.java)

        @Provides
        @Singleton
        fun provideResourceProvider(@ApplicationContext context: Context) =
            ResourceProvider(context)

        @Provides
        @Singleton
        fun provideNetworkHelper(@ApplicationContext context: Context) = NetworkHelper(context)
    }
}