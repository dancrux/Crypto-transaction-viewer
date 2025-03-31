package com.example.cryptotransactionviewer.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cryptotransactionviewer.data.local.entity.BlockEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BlockDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBlock(block: BlockEntity)

    @Query("SELECT * FROM blocks ORDER BY time DESC LIMIT 1")
    fun getLatestBlock(): Flow<BlockEntity>

    @Query("SELECT * FROM blocks WHERE hash = :hash")
    fun getBlockByHash(hash: String): Flow<BlockEntity?>

    @Query("DELETE FROM blocks")
    suspend fun clearBlocks()
}