package com.yoshio.test.di

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import com.yoshio.test.BuildConfig
import com.yoshio.test.data.Network.LoginRemote
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

@Module
@InstallIn(SingletonComponent::class)
object ApolloModule {

    @Singleton
    @Provides
    fun provideLoginRemoteApollo(): ApolloClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        var okHttpClient = OkHttpClient
                .Builder()
                .addInterceptor(logging)

        return ApolloClient.Builder()
                .serverUrl(BuildConfig.SERVER_URL)
                .okHttpClient(okHttpClient.build())
                .build()
    }

    @Singleton
    @Provides
    fun provideApolloClient() = LoginRemote(client = provideLoginRemoteApollo())


}