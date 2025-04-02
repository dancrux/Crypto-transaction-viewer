package com.example.cryptotransactionviewer.presentation.transactions

import com.example.cryptotransactionviewer.domain.model.Transaction

sealed class TransactionsUiState {
    object Initial : TransactionsUiState()
    object Loading : TransactionsUiState()
    data class Success(val transactions: List<Transaction>) : TransactionsUiState()
    data class Error(val message: String) : TransactionsUiState()
}