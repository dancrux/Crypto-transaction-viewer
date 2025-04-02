package com.example.cryptotransactionviewer.presentation.login

import com.example.cryptotransactionviewer.domain.model.User

sealed class LoginUiState {
    object  Initial: LoginUiState()
    object Loading: LoginUiState()
    data class Success(val user: User): LoginUiState()
    data class Error(val message: String): LoginUiState()
}