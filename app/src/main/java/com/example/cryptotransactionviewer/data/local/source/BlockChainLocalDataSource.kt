package com.example.cryptotransactionviewer.data.local.source

import com.example.cryptotransactionviewer.data.local.dao.BlockDao
import com.example.cryptotransactionviewer.data.local.dao.TransactionDao
import com.example.cryptotransactionviewer.data.local.entity.BlockEntity
import com.example.cryptotransactionviewer.data.local.entity.TransactionEntity
import com.example.cryptotransactionviewer.data.local.entity.TransactionInputEntity
import com.example.cryptotransactionviewer.data.local.entity.TransactionOutputEntity
import com.example.cryptotransactionviewer.data.local.entity.TransactionWithDetails
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BlockChainLocalDataSource @Inject constructor(
    private val blockDao: BlockDao,
    private val transactionDao: TransactionDao
) {

    fun getLatestBlock(): Flow<BlockEntity?> {
        return blockDao.getLatestBlock()
    }

    fun getBlockByHash(hash: String): Flow<BlockEntity?> {
        return blockDao.getBlockByHash(hash)
    }

    suspend fun saveBlock(block: BlockEntity) {
        blockDao.insertBlock(block)
    }

    fun getTransactionsByBlockHash(blockHash: String): Flow<List<TransactionWithDetails>> {
        return transactionDao.getTransactionsByBlockHash(blockHash)
    }

    suspend fun saveTransactions(
        transactionEntities: List<TransactionEntity>,
        inputEntities: List<TransactionInputEntity>,
        outputEntities: List<TransactionOutputEntity>
    ) {
        transactionEntities.forEach { transaction ->
            transactionDao.insertTransaction(transaction)
        }
        transactionDao.insertTransactionInputs(inputEntities)
        transactionDao.insertTransactionOutputs(outputEntities)
    }

    suspend fun clearTransactionsForBlock(blockHash: String) {
        transactionDao.clearTransactionsForBlock(blockHash)
    }

    suspend fun clearBlocks() {
        blockDao.clearBlocks()
    }

    suspend fun clearAllTransactions() {
        transactionDao.clearAllTransactions()
    }
}