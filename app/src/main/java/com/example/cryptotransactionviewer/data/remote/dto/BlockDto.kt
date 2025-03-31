package com.example.cryptotransactionviewer.data.remote.dto

import com.example.cryptotransactionviewer.domain.model.Block
import com.google.gson.annotations.SerializedName

data class BlockDto(
    @SerializedName("hash") val hash: String,
    @SerializedName( "height") val height: Long,
    @SerializedName("time") val time: Long,
    @SerializedName("tx_count") val txCount: Int
)
fun BlockDto.toBlock(): Block {
    return Block(
        hash = hash,
        height = height,
        time = time,
        transactionCount = txCount
    )
}