package com.example.cryptotransactionviewer.domain.model

data class Transaction(
    val hash: String,
    val time: Long,
    val blockHeight: Long,
    val inputs: List<TransactionInput>,
    val outputs: List<TransactionOutput>,
    val fee: Long,
    val size: Int
)

data class TransactionInput(
    val previousOutput: String,
    val address: String?,
    val value: Long
)
data class TransactionOutput(
    val address: String?,
    val value: Long
)