package me.saurabhrane.mvp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import me.saurabhrane.mvp.BuildConfig
import me.saurabhrane.mvp.network.ApiKeyInterceptor
import me.saurabhrane.mvp.network.MoviesApiService
import me.saurabhrane.mvp.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideApiKeyInterceptor() : ApiKeyInterceptor {
        return ApiKeyInterceptor()
    }

    @Singleton
    @Provides
    fun provideOkHttpClientWithInterceptor(apiKeyInterceptor: ApiKeyInterceptor) : OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(apiKeyInterceptor).addInterceptor(
            HttpLoggingInterceptor().apply {
                level = when(BuildConfig.DEBUG){
                    true -> HttpLoggingInterceptor.Level.BODY
                    false -> HttpLoggingInterceptor.Level.NONE
                }
            }
        ).build()
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilder(okHttpClient: OkHttpClient) : Retrofit.Builder{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
    }

    @Singleton
    @Provides
    fun provideApiService(builder: Retrofit.Builder) : MoviesApiService{
        return builder.build().create(MoviesApiService::class.java)
    }

}