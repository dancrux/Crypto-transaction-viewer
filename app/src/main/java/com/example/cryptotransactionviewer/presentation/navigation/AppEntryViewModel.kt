package com.example.cryptotransactionviewer.presentation.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AppEntryViewModel @Inject constructor() :ViewModel() {
    private val _startDestination = MutableStateFlow<String?>(Screen.Login.route)
    val startDestination = _startDestination.asStateFlow()

    fun determineStartDestination(appNavigator: AppNavigator){
      try {
          viewModelScope.launch {
              _startDestination.value = appNavigator.getStartDestination()
          }
      }catch (e: Exception){
          // Fallback to login screen
          _startDestination.value = Screen.Login.route

      }

    }
}