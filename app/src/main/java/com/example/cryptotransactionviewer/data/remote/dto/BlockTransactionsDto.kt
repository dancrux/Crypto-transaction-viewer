package com.example.cryptotransactionviewer.data.remote.dto

import com.example.cryptotransactionviewer.domain.model.Transaction
import com.example.cryptotransactionviewer.domain.model.TransactionInput
import com.example.cryptotransactionviewer.domain.model.TransactionOutput
import com.google.gson.annotations.SerializedName

data class BlockTransactionsDto(
    @SerializedName("hash") val hash: String,
    @SerializedName("tx") val transactions: List<TransactionDto>
)

data class TransactionDto(
    @SerializedName("hash") val hash: String,
    @SerializedName("time") val time: Long,
    @SerializedName("block_height") val blockHeight: Long,
    @SerializedName("inputs") val inputs: List<TransactionInputDto>,
    @SerializedName("out") val outputs: List<TransactionOutputDto>,
    @SerializedName("fee") val fee: Long,
    @SerializedName("size") val size: Int
)
data class TransactionInputDto(
    @SerializedName("prev_out") val previousOutput: PreviousOutputDto?
)

data class PreviousOutputDto(
    @SerializedName( "addr") val address: String?,
    @SerializedName("value") val value: Long
)

data class TransactionOutputDto(
    @SerializedName("addr") val address: String?,
    @SerializedName("value") val value: Long
)

fun TransactionDto.toTransaction(): Transaction {
    return Transaction(
        hash = hash,
        time = time,
        blockHeight = blockHeight,
        inputs = inputs.mapNotNull { input ->
            input.previousOutput?.let {
                TransactionInput(
                    previousOutput = it.address ?: "",
                    address = it.address,
                    value = it.value
                )
            }
        },
        outputs = outputs.map { output ->
            TransactionOutput(
                address = output.address,
                value = output.value
            )
        },
        fee = fee,
        size = size
    )
}
