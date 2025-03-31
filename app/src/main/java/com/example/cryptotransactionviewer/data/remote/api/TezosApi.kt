package com.example.cryptotransactionviewer.data.remote.api

import com.example.cryptotransactionviewer.data.remote.dto.TezosBlockDto
import com.example.cryptotransactionviewer.data.remote.dto.TezosOperationDto
import retrofit2.http.GET
import retrofit2.http.Path

interface TezosApi {

    @GET("v1/blocks?limit=1")
    suspend fun getLatestBlock(): List<TezosBlockDto>

    @GET("v1/blocks/{level}/operations")
    suspend fun getBlockOperations(@Path("level") level:Long): List<TezosOperationDto>

}