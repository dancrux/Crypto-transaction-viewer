package com.example.cryptotransactionviewer.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.example.cryptotransactionviewer.domain.model.Transaction
import com.example.cryptotransactionviewer.domain.model.TransactionInput
import com.example.cryptotransactionviewer.domain.model.TransactionOutput


@Entity(
    tableName = "transactions",
    foreignKeys = [
        ForeignKey(
            entity = BlockEntity::class,
            parentColumns = ["hash"],
            childColumns = ["block_hash"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class TransactionEntity(
    @PrimaryKey
    val hash: String,
    val time: Long,
    val blockHeight: Long,
    val block_hash: String,
    val fee: Long,
    val size: Int
)

@Entity(
    tableName = "transaction_inputs",
    foreignKeys = [
        ForeignKey(
            entity = TransactionEntity::class,
            parentColumns = ["hash"],
            childColumns = ["transactionHash"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class TransactionInputEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val transactionHash: String,
    val previousOutput: String,
    val address: String?,
    val value: Long
)

@Entity(
    tableName = "transaction_outputs",
    foreignKeys = [
        ForeignKey(
            entity = TransactionEntity::class,
            parentColumns = ["hash"],
            childColumns = ["transactionHash"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class TransactionOutputEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val transactionHash: String,
    val address: String?,
    val value: Long
)
data class TransactionWithDetails(
    @Embedded val transaction: TransactionEntity,
    @Relation(
        parentColumn = "hash",
        entityColumn = "transactionHash"
    )
    val inputs: List<TransactionInputEntity>,
    @Relation(
        parentColumn = "hash",
        entityColumn = "transactionHash"
    )
    val outputs: List<TransactionOutputEntity>
)

//Mapper Functions
fun TransactionWithDetails.toTransaction(): Transaction {
    return Transaction(
        hash = transaction.hash,
        time = transaction.time,
        blockHeight = transaction.blockHeight,
        inputs = inputs.map { input ->
            TransactionInput(
                previousOutput = input.previousOutput,
                address = input.address,
                value = input.value
            )
        },
        outputs = outputs.map { output ->
            TransactionOutput(
                address = output.address,
                value = output.value
            )
        },
        fee = transaction.fee,
        size = transaction.size
    )
}

fun Transaction.toTransactionEntity(blockHash: String): TransactionEntity {
    return TransactionEntity(
        hash = hash,
        time = time,
        blockHeight = blockHeight,
        block_hash = blockHash,
        fee = fee,
        size = size
    )
}

fun Transaction.toInputEntities(): List<TransactionInputEntity> {
    return inputs.map { input ->
        TransactionInputEntity(
            transactionHash = hash,
            previousOutput = input.previousOutput,
            address = input.address,
            value = input.value
        )
    }
}


fun Transaction.toOutputEntities(): List<TransactionOutputEntity> {
    return outputs.map { output ->
        TransactionOutputEntity(
            transactionHash = hash,
            address = output.address,
            value = output.value
        )
    }
}
