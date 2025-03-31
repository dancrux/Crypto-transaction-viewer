package com.example.cryptotransactionviewer.data.local.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.cryptotransactionviewer.data.local.entity.TransactionEntity
import com.example.cryptotransactionviewer.data.local.entity.TransactionInputEntity
import com.example.cryptotransactionviewer.data.local.entity.TransactionOutputEntity
import com.example.cryptotransactionviewer.data.local.entity.TransactionWithDetails
import kotlinx.coroutines.flow.Flow

interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: TransactionEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransactionInputs(inputs: List<TransactionInputEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransactionOutputs(outputs: List<TransactionOutputEntity>)

    @Transaction
    @Query("SELECT * FROM transactions WHERE block_hash = :blockHash")
    fun getTransactionsByBlockHash(blockHash: String): Flow<List<TransactionWithDetails>>

    @Query("DELETE FROM transactions WHERE block_hash = :blockHash")
    suspend fun clearTransactionsForBlock(blockHash: String)

    @Query("DELETE FROM transactions")
    suspend fun clearAllTransactions()
}