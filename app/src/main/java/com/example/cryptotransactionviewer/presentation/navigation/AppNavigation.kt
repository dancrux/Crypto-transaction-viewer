package com.example.cryptotransactionviewer.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cryptotransactionviewer.domain.usecase.IsLoggedInUseCase
import javax.inject.Inject

sealed class Screen(val route: String){
    object Login: Screen("login")
    object Transactions: Screen("transactions")
}

class AppNavigator @Inject constructor (
  private val isLoggedInUseCase: IsLoggedInUseCase
){
    suspend fun getStartDestination(): String{
        var startDestination = Screen.Login.route

        isLoggedInUseCase().collect{ isLoggedIn ->
            startDestination = if (isLoggedIn){
                Screen.Transactions.route
            }else Screen.Login.route
        }
        return startDestination
    }
}

@Composable
fun AppEntryPoint(
    appNavigator: AppNavigator
){
    val viewModel: AppEntryViewModel = hiltViewModel()
    val startDestination by viewModel.startDestination.collectAsState()
    
    LaunchedEffect(Unit) {
        viewModel.determineStartDestination(appNavigator)
    }

    startDestination?.let { destination->
        AppNavigation(appNavigator = appNavigator, startDestination = destination)

    }
}

@Composable
fun AppNavigation(
    appNavigator: AppNavigator,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Login.route
){
            NavHost(navController = navController, startDestination = startDestination) {
                composable(Screen.Login.route){
//                    LoginScreen(
//                        onLoginSuccess = {
//                            navController.navigate(Screen.Transactions.route) {
//                                popUpTo(Screen.Login.route) { inclusive = true }
//                            }
//                        }
//                    )
         }
                composable(Screen.Transactions.route) {
//                    TransactionsScreen(
//                        onLogout = {
//                            navController.navigate(Screen.Login.route) {
//                                popUpTo(Screen.Transactions.route) { inclusive = true }
//                            }
//                        }
//                    )
                }
    }
}