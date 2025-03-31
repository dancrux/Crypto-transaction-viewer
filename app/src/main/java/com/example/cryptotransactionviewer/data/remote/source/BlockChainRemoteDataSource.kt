package com.example.cryptotransactionviewer.data.remote.source

import com.example.cryptotransactionviewer.data.remote.api.BlockChainApi
import com.example.cryptotransactionviewer.data.remote.api.TezosApi
import com.example.cryptotransactionviewer.data.remote.dto.BlockDto
import com.example.cryptotransactionviewer.data.remote.dto.BlockTransactionsDto
import com.example.cryptotransactionviewer.data.remote.dto.TezosBlockDto
import com.example.cryptotransactionviewer.data.remote.dto.TezosOperationDto
import javax.inject.Inject

class BlockChainRemoteDataSource @Inject constructor(
    private val blockchainApi: BlockChainApi,
    private val tezosApi: TezosApi
) {

    suspend fun getLatestBlock(): BlockDto{
        return blockchainApi.getLatestBlock()
    }

    suspend fun getBlockTransactions(blockHash: String): BlockTransactionsDto {
        return blockchainApi.getBlockTransactions(blockHash)
    }

    suspend fun getLatestTezosBlock(): List<TezosBlockDto> {
        return tezosApi.getLatestBlock()
    }

    suspend fun getTezosBlockOperations(level: Long): List<TezosOperationDto> {
        return tezosApi.getBlockOperations(level)
    }
}