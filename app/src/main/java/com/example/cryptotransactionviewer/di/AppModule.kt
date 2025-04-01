package com.example.cryptotransactionviewer.di

import com.example.cryptotransactionviewer.data.local.source.BlockChainLocalDataSource
import com.example.cryptotransactionviewer.data.local.source.UserLocalDataSource
import com.example.cryptotransactionviewer.data.remote.source.BlockChainRemoteDataSource
import com.example.cryptotransactionviewer.data.remote.source.UserRemoteDataSource
import com.example.cryptotransactionviewer.data.repositoryimpl.BlockChainRepositoryImpl
import com.example.cryptotransactionviewer.data.repositoryimpl.UserRepositoryImpl
import com.example.cryptotransactionviewer.domain.repository.BlockChainRepository
import com.example.cryptotransactionviewer.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

//    @Provides
//    @Singleton
//    fun provideAppNavigator(): AppNavigator {
//        return AppNavigator()
//    }

    @Provides
    @Singleton
    fun provideBlockChainRepository(
        remoteDataSource: BlockChainRemoteDataSource,
        localDataSource: BlockChainLocalDataSource
    ): BlockChainRepository{
        return BlockChainRepositoryImpl(remoteDataSource, localDataSource)
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        remoteDataSource: UserRemoteDataSource,
        localDataSource: UserLocalDataSource
    ): UserRepository {
        return UserRepositoryImpl(remoteDataSource, localDataSource)
    }
}