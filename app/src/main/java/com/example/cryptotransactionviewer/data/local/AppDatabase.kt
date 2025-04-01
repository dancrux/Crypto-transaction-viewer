package com.example.cryptotransactionviewer.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.cryptotransactionviewer.data.local.dao.BlockDao
import com.example.cryptotransactionviewer.data.local.dao.TransactionDao
import com.example.cryptotransactionviewer.data.local.dao.UserDao
import com.example.cryptotransactionviewer.data.local.entity.BlockEntity
import com.example.cryptotransactionviewer.data.local.entity.TransactionEntity
import com.example.cryptotransactionviewer.data.local.entity.TransactionInputEntity
import com.example.cryptotransactionviewer.data.local.entity.TransactionOutputEntity
import com.example.cryptotransactionviewer.data.local.entity.UserEntity
import com.example.cryptotransactionviewer.data.local.util.Converters


@Database(
    entities = [
        BlockEntity::class,
        TransactionEntity::class,
        TransactionInputEntity::class,
        TransactionOutputEntity::class,
        UserEntity::class
               ],
    version = 1,
    exportSchema = false

)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun blockDao(): BlockDao
    abstract fun transactionDao(): TransactionDao
    abstract fun userDao(): UserDao
}