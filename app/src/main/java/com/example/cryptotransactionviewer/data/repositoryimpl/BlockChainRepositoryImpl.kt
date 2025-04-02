package com.example.cryptotransactionviewer.data.repositoryimpl

import android.util.Log
import com.example.cryptotransactionviewer.data.local.entity.toBlock
import com.example.cryptotransactionviewer.data.local.entity.toBlockEntity
import com.example.cryptotransactionviewer.data.local.entity.toInputEntities
import com.example.cryptotransactionviewer.data.local.entity.toOutputEntities
import com.example.cryptotransactionviewer.data.local.entity.toTransaction
import com.example.cryptotransactionviewer.data.local.entity.toTransactionEntity
import com.example.cryptotransactionviewer.data.local.source.BlockChainLocalDataSource
import com.example.cryptotransactionviewer.data.remote.dto.toBlock
import com.example.cryptotransactionviewer.data.remote.dto.toTransaction
import com.example.cryptotransactionviewer.data.remote.source.BlockChainRemoteDataSource
import com.example.cryptotransactionviewer.domain.model.Block
import com.example.cryptotransactionviewer.domain.model.Resource
import com.example.cryptotransactionviewer.domain.model.Transaction
import com.example.cryptotransactionviewer.domain.model.TransactionInput
import com.example.cryptotransactionviewer.domain.model.TransactionOutput
import com.example.cryptotransactionviewer.domain.repository.BlockChainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BlockChainRepositoryImpl @Inject constructor(
    private val remoteDataSource: BlockChainRemoteDataSource,
    private val localDataSource: BlockChainLocalDataSource
) : BlockChainRepository {
    override suspend fun getLatestBlock(): Flow<Resource<Block>> = flow {
      emit(Resource.Loading())

        // Check for cached data first
        try {
            val currentTimeMillis: Long = System.currentTimeMillis()
            val futureTimeMillis: Long = currentTimeMillis + (2 * 60 * 1000) // 5 minutes in ms
            val cachedBlock = localDataSource.getLatestBlock().firstOrNull()

            if (cachedBlock != null && cachedBlock.time > futureTimeMillis){
                emit(Resource.Success(cachedBlock.toBlock()))
                return@flow
            }
            else{
                //        Fetch from remote sources
                val remoteBlock = remoteDataSource.getLatestBlock()

                // Cache to local database
                localDataSource.saveBlock(remoteBlock.toBlock().toBlockEntity())

                emit(Resource.Success(remoteBlock.toBlock()))
            }
        }
        catch (e: HttpException){
            emit(Resource.Error("Failed to fetch latest block: ${e.localizedMessage}"))
        }catch (e: IOException){
            emit(Resource.Error("Network error: ${e.localizedMessage}"))
        }catch (e: Exception){
            emit(Resource.Error("Unknown error: ${e.localizedMessage}"))
        }
    }

    override suspend fun getBlockTransactions(blockHash: String): Flow<Resource<List<Transaction>>> = flow{
    emit(Resource.Loading())

        try {
            val cachedTransaction = localDataSource.getTransactionsByBlockHash(blockHash).firstOrNull()
            if (!cachedTransaction.isNullOrEmpty()){
                emit(Resource.Success(cachedTransaction.map { it.toTransaction() }))
                return@flow
            }else {
                //            Fetch from remote
                val blockTransactions = remoteDataSource.getBlockTransactions(blockHash)
                val transactions = blockTransactions.transactions.map {
                    Log.d("getLatestBlock", "remoteBlock Has Data ${it.fee}")
                    it.toTransaction()
                }
                // Cache to local database
                localDataSource.clearTransactionsForBlock(blockHash)

                val firstHundredAndFifty = transactions.take(150)

                val transactionEntities =
                    firstHundredAndFifty.map { it.toTransactionEntity(blockHash) }
                val inputEntities = firstHundredAndFifty.flatMap { it.toInputEntities() }
                val outputEntities = firstHundredAndFifty.flatMap { it.toOutputEntities() }


                localDataSource.saveTransactions(
                    transactionEntities.take(25),
                    inputEntities.take(25),
                    outputEntities.take(25)
                )

                // Emit success with remote data
                emit(Resource.Success(transactions.take(50)))
            }

        }catch (e: HttpException) {
            emit(Resource.Error("Failed to fetch transactions: ${e.localizedMessage}"))
        } catch (e: IOException) {
            emit(Resource.Error("Network error: ${e.localizedMessage}"))
        } catch (e: Exception) {
            emit(Resource.Error("Unknown error: ${e.localizedMessage}"))
        }
    }

    override suspend fun getTezosBlockTransactions(): Flow<Resource<List<Transaction>>> = flow {
        emit(Resource.Loading())
        try {
            val latestBlocks = remoteDataSource.getLatestTezosBlock()
            if (latestBlocks.isEmpty()){
                emit(Resource.Error("No Tezos blocks found"))
                return@flow
            }
//get the most recent block
            val newestBlock = latestBlocks.first()

            // Fetch operations for the block
            val blockOperations = remoteDataSource.getTezosBlockOperations(newestBlock.level)

            // Convert to domain model
            val transactions = blockOperations.filter { it.type == "transaction" }.mapIndexed { _, tezosOperationDto ->
                Transaction(
                    hash = tezosOperationDto.hash, time = Date().time, blockHeight = newestBlock.level, inputs = listOf(
                    TransactionInput(
                        previousOutput = tezosOperationDto.sender?.address ?: "",
                        address = tezosOperationDto.sender?.address,
                        value = tezosOperationDto.amount ?: 0
                    )
                ), outputs = listOf(TransactionOutput(
                    address = tezosOperationDto.target?.address, value = tezosOperationDto.amount ?:0
                )
                ), fee = 0, size = 0
            )
            }
            emit(Resource.Success(transactions))
        }catch (e: HttpException) {
            emit(Resource.Error("Failed to fetch Tezos transactions: ${e.localizedMessage}"))
        } catch (e: IOException) {
            emit(Resource.Error("Network error: ${e.localizedMessage}"))
        } catch (e: Exception) {
            emit(Resource.Error("Unknown error: ${e.localizedMessage}"))
        }
    }

}