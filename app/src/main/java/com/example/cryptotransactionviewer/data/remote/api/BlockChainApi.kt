package com.example.cryptotransactionviewer.data.remote.api

import com.example.cryptotransactionviewer.data.remote.dto.BlockDto
import com.example.cryptotransactionviewer.data.remote.dto.BlockTransactionsDto
import retrofit2.http.GET
import retrofit2.http.Path

interface BlockChainApi {

      @GET("latestblock")
    suspend fun getLatestBlock(): BlockDto

    @GET("rawblock/{blockHash}")
    suspend fun getBlockTransactions(@Path("blockHash") blockHash: String): BlockTransactionsDto
}