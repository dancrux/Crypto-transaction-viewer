package com.example.cryptotransactionviewer.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptotransactionviewer.R
import com.example.cryptotransactionviewer.domain.model.Resource
import com.example.cryptotransactionviewer.domain.model.User
import com.example.cryptotransactionviewer.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
): ViewModel() {
    private  val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Initial)
    val uiState : StateFlow<LoginUiState> = _uiState.asStateFlow()

//   used for username and password values
    private val _username = MutableStateFlow("")
    val username = _username.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

//Functions for updating values from ui
    fun updateUsername(username: String) {
        _username.value = username
    }

    fun updatePassword(password: String) {
        _password.value = password
    }

//    TODO: COMPLETE LOGIN SETUP
    fun login(){
        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading

            //Do Simple  validation
            if (_username.value.isBlank() && _password.value.isBlank()){
                _uiState.value = LoginUiState.Error("Username and password cannot be empty")
                return@launch
            }

            loginUseCase(_username.value, _password.value).collect{ result->
                _uiState.value = when(result){
                    is Resource.Loading -> LoginUiState.Loading
                    is Resource.Success -> LoginUiState.Success(result.data!!)
                    is Resource.Error -> LoginUiState.Error(result.message  ?:R.string.general_error_message.toString())

                }


            }
        }
    }

    // For demo purposes, allow a mock login with hardcoded credentials
    fun mockLogin() {
        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading

            // Simulate API delay
            kotlinx.coroutines.delay(2000)

            if (_username.value == "demo" && _password.value == "password") {
                _uiState.value = LoginUiState.Success(
                   User(
                        id = "mock-user-id",
                        username = _username.value,
                        email = "demo@example.com",
                        token = "mock-token-12345"
                    )
                )
            } else {
                _uiState.value = LoginUiState.Error("Invalid username or password")
            }
        }
    }
}