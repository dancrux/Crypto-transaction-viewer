package com.example.cryptotransactionviewer.presentation.transactions

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptotransactionviewer.R
import com.example.cryptotransactionviewer.domain.model.Block
import com.example.cryptotransactionviewer.domain.model.Resource
import com.example.cryptotransactionviewer.domain.usecase.GetLatestBlockUseCase
import com.example.cryptotransactionviewer.domain.usecase.GetTransactionsUseCase
import com.example.cryptotransactionviewer.domain.usecase.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionsViewModel @Inject constructor(
    private val getLatestBlockUseCase: GetLatestBlockUseCase,
    private val getTransactionsUseCase: GetTransactionsUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel(){
    private val _uiState = MutableStateFlow<TransactionsUiState>(TransactionsUiState.Initial)
    val uiState: StateFlow<TransactionsUiState> = _uiState.asStateFlow()

    private val _currentBlock= MutableStateFlow<Block?>(null)
    val currentBlock = _currentBlock.asStateFlow()

    init {
        loadLatestBlock()
    }

    fun loadLatestBlock(){
        viewModelScope.launch {
            _uiState.value = TransactionsUiState.Loading
            getLatestBlockUseCase().collect{ result->
                when(result){
                   is Resource.Loading -> {
                       _uiState.value = TransactionsUiState.Loading
                   }
                    is Resource.Success -> {
                        _currentBlock.value = result.data
                        result.data?.let { loadTransactions(it.hash) }
                        Log.d("transactions latestBlock" , "LatestBlock fetched")
                    }
                    is Resource.Error ->{
                        _uiState.value = TransactionsUiState.Error(result.message ?: R.string.general_error_message.toString())
                    }
                }

            }
        }
    }

    private fun loadTransactions(blockHash: String){
        viewModelScope.launch {
            getTransactionsUseCase(blockHash).collect{ result->
                when (result){
                    is Resource.Loading -> _uiState.value = TransactionsUiState.Loading
                    is Resource.Success -> _uiState.value = TransactionsUiState.Success(result.data ?: emptyList())
                    is Resource.Error ->  _uiState.value= TransactionsUiState.Error(result.message ?: R.string.general_error_message.toString())
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            logoutUseCase().collect {
                println("Logout done")
                // We don't update UI state for logout as it will be handled by navigation
            }
        }
    }
}