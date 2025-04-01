package com.example.cryptotransactionviewer.di

import android.content.Context
import androidx.room.Room
import com.example.cryptotransactionviewer.data.local.AppDatabase
import com.example.cryptotransactionviewer.data.local.dao.BlockDao
import com.example.cryptotransactionviewer.data.local.dao.TransactionDao
import com.example.cryptotransactionviewer.data.local.dao.UserDao
import com.example.cryptotransactionviewer.data.local.source.BlockChainLocalDataSource
import com.example.cryptotransactionviewer.data.local.source.UserLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDataBase(@ApplicationContext context: Context): AppDatabase{
        return Room.databaseBuilder(context, AppDatabase::class.java,  "bitcoin_transactions.db")
            .fallbackToDestructiveMigration()
            .build()
    }


    @Provides
    @Singleton
    fun provideBlockDao(database: AppDatabase): BlockDao {
        return database.blockDao()
    }

    @Provides
    @Singleton
    fun provideTransactionDao(database: AppDatabase): TransactionDao {
        return database.transactionDao()
    }

    @Provides
    @Singleton
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }
    @Provides
    @Singleton
    fun provideBlockChainLocalDataSource(blockDao: BlockDao, transactionDao: TransactionDao): BlockChainLocalDataSource{
        return BlockChainLocalDataSource(blockDao, transactionDao)
    }
    @Provides
    @Singleton
    fun provideUserLocalDataSource(userDao: UserDao): UserLocalDataSource {
        return UserLocalDataSource(userDao)
    }
}