package com.example.cryptotransactionviewer

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.cryptotransactionviewer.presentation.navigation.AppEntryPoint
import com.example.cryptotransactionviewer.presentation.navigation.AppNavigator
import com.example.cryptotransactionviewer.presentation.theme.CryptoTransactionViewerTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var appNavigator: AppNavigator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        enableEdgeToEdge()
        setContent {
            Log.d("AppFlow", "MainActivity setContent start")
            CryptoTransactionViewerTheme {
                Log.d("AppFlow", "Inside BitcoinTransactionsTheme")
                Surface(modifier = Modifier.fillMaxSize()) {
                    Log.d("AppFlow", "About to call AppEntryPoint")
                   AppEntryPoint(appNavigator = appNavigator)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CryptoTransactionViewerTheme {
        Greeting("Checks")
    }
}