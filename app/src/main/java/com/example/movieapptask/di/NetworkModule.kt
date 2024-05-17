package com.example.movieapptask.di

import com.example.data.constants.Constants
import com.example.data.datasource.remote.service.MovieService
import com.example.data.interceptors.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient().newBuilder()
            .connectTimeout(15, TimeUnit.MINUTES)
            .readTimeout(15, TimeUnit.MINUTES)
            .writeTimeout(15, TimeUnit.MINUTES)
            .retryOnConnectionFailure(true)
            .addInterceptor(logger)
            .addInterceptor(AuthInterceptor("eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmOWZlMTI4ZTFmNmM0NWRkODM1MjY2MjViNDAyZWMwZiIsInN1YiI6IjYzYTcwNjMwMmZhZjRkMDBiMWU4MGJmZCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.Yg8vR2_EpYmYx87KSvHqNbWWTj7IOXuWMTyWb5Ykq-w"))
            .build()
    }


    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit.Builder =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)

    @Provides
    @Singleton
    fun provideMoviesService(retrofitBuilder: Retrofit.Builder): MovieService =
        retrofitBuilder.build().create(MovieService::class.java)


}