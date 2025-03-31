package com.example.cryptotransactionviewer.domain.usecase

import com.example.cryptotransactionviewer.domain.model.Resource
import com.example.cryptotransactionviewer.domain.model.Transaction
import com.example.cryptotransactionviewer.domain.repository.BlockChainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTezosTransactionsUseCase @Inject constructor(
    private val blockChainRepository: BlockChainRepository
) {

    suspend  operator fun invoke (): Flow<Resource<List<Transaction>>>{
        return blockChainRepository.getTezosBlockTransactions()
    }

}