package com.example.cryptotransactionviewer.domain.repository

import com.example.cryptotransactionviewer.domain.model.Block
import com.example.cryptotransactionviewer.domain.model.Resource
import com.example.cryptotransactionviewer.domain.model.Transaction
import kotlinx.coroutines.flow.Flow

interface BlockChainRepository {
    suspend fun getLatestBlock(): Flow<Resource<Block>>
    suspend fun getBlockTransactions(blockHash: String): Flow<Resource<List<Transaction>>>
    suspend fun getTezosBlockTransactions(): Flow<Resource<List<Transaction>>>
}