package com.example.cryptotransactionviewer.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cryptotransactionviewer.domain.model.Block

@Entity(tableName = "blocks")
data class BlockEntity(
    @PrimaryKey
    val hash: String,
    val height: Long,
    val time: Long,
    val transactionCount: Int
)

fun BlockEntity.toBlock(): Block {
    return Block(
        hash = hash,
        height = height,
        time = time,
        transactionCount = transactionCount
    )
}

fun Block.toBlockEntity(): BlockEntity {
    return BlockEntity(
        hash = hash,
        height = height,
        time = time,
        transactionCount = transactionCount
    )
}
