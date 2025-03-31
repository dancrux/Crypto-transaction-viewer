package com.example.cryptotransactionviewer.domain.model

data class Block(
    val hash: String,
    val height: Long,
    val time: Long,
    val transactionCount: Int
)
