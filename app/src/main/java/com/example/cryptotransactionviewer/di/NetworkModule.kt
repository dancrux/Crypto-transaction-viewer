package com.example.cryptotransactionviewer.di

import com.example.cryptotransactionviewer.data.remote.api.BlockChainApi
import com.example.cryptotransactionviewer.data.remote.api.TezosApi
import com.example.cryptotransactionviewer.data.remote.source.BlockChainRemoteDataSource
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    @Named("blockChainRetrofit")
    fun provideBlockChainRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://blockchain.info/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
    @Provides
    @Singleton
    @Named("tezosRetrofit")
    fun provideTezosRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.tzkt.io/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideBlockChainApi(@Named("blockChainRetrofit") retrofit: Retrofit): BlockChainApi{
        return retrofit.create(BlockChainApi::class.java)
    }
    @Provides
    @Singleton
    fun provideTezosApi(@Named("tezosRetrofit") retrofit: Retrofit): TezosApi {
        return retrofit.create(TezosApi::class.java)
    }

    @Provides
    @Singleton
    fun provideBlockChainRemoteDataSource(blockChainApi: BlockChainApi, tezosApi: TezosApi): BlockChainRemoteDataSource{
        return BlockChainRemoteDataSource(blockChainApi, tezosApi)
    }
}