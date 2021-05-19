package com.revnarayan.rogerstimburtonsassignment.di

import com.google.gson.GsonBuilder
import com.revnarayan.rogerstimburtonsassignment.network.ApiInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

const val BASE_URL = "https://api.timburtons.com/"
const val PRODUCTS_END_POINT = "v1/products"

@InstallIn(ApplicationComponent::class)
@Module
object NetworkModule {


    @Provides
    fun provideHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .build()

    @Provides
    fun provideRetrofit(client: OkHttpClient): ApiInterface = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .client(client)
        .build()
        .create(ApiInterface::class.java)
}
