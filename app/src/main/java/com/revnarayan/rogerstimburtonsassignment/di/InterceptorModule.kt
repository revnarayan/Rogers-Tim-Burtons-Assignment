package com.revnarayan.rogerstimburtonsassignment.di

import com.revnarayan.rogerstimburtonsassignment.network.MockResponseInterceptor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.Interceptor
import javax.inject.Named

@InstallIn(ApplicationComponent::class)
@Module
abstract class InterceptorModule {
    @Named(MOCK_RESPONSE_INTERCEPTOR)
    @Binds
    abstract fun provideCacheInterceptor(interceptor: MockResponseInterceptor): Interceptor
}