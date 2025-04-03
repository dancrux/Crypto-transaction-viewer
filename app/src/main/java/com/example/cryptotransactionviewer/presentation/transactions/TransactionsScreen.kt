package com.example.cryptotransactionviewer.presentation.transactions

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cryptotransactionviewer.R
import com.example.cryptotransactionviewer.domain.model.Block
import com.example.cryptotransactionviewer.presentation.components.AppTopBar
import com.example.cryptotransactionviewer.presentation.components.ErrorMessage
import com.example.cryptotransactionviewer.presentation.components.LoadingIndicator
import com.example.cryptotransactionviewer.presentation.components.TransactionItem
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun TransactionsScreen (
    onLogout: ()-> Unit,
    viewModel: TransactionsViewModel = hiltViewModel()
){
    val uiState by viewModel.uiState.collectAsState()
    val currentBlock by viewModel.currentBlock.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    
    Scaffold (
        topBar = {
            AppTopBar(
                title = stringResource(id = R.string.bitcoin_transactions),
                showLogoutButton = true,
                onLogoutClick = {
                    viewModel.logout()
                    onLogout()
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackBarHostState)},
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.loadLatestBlock()
                    Log.d("transactionScreen FAB click" , "loadLatestBlock called")
                          },
                containerColor = MaterialTheme.colorScheme.primary
            )
            {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = stringResource(id = R.string.refresh),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }

        },
    ) { paddingValues -> 
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ){
            Column (
                modifier = Modifier.fillMaxSize()
            ){
//                Handles showing Block Info
                currentBlock?.let {block->
                    BlockInfoCard(block = block)
                }
                Spacer(modifier = Modifier.height(8.dp))
                when(uiState){
                    is TransactionsUiState.Loading -> {
                        LoadingIndicator()
                    }
                    is TransactionsUiState.Success -> {
                        val transactions = (uiState as TransactionsUiState.Success).transactions
                        if (transactions.isEmpty()){
                            Box(modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center){
                                Text(
                                    text = stringResource(id = R.string.empty_transactions_text),
                                    style = MaterialTheme.typography.bodyLarge,
                                    textAlign = TextAlign.Center
                                )

                            }
                        }else{
                            LazyColumn (
                                modifier = Modifier.fillMaxSize(),
                                contentPadding = PaddingValues(bottom = 88.dp)
                            ){
                                items(transactions){ transaction->
                                    TransactionItem(transaction = transaction)
                                }
                            }
                        }
                    }
                    is TransactionsUiState.Error -> {
                        ErrorMessage(
                            message = (uiState as TransactionsUiState.Error).message,
                            onRetry = {
                                viewModel.loadLatestBlock()

                        }
                        )
                    }
                    else ->{}
                }

            }
        }
        
    }
}

@Composable
fun BlockInfoCard(
    block: Block,

){
    val dateFormat = SimpleDateFormat("MMMM dd, yyyy HH:mm:ss", Locale.getDefault())
    val formattedDate = dateFormat.format(Date(block.time * 1000))


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ){
            Text(
                text = "Current Block",
                style = MaterialTheme.typography.titleMedium,

            )
            Spacer(modifier = Modifier.height(8.dp))

            Row (
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = stringResource(id = R.string.height),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                    Text(
                        text = block.height.toString(),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        textAlign = TextAlign.Center
                    )
                }
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = stringResource(id = R.string.transactions),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                    Text(
                        text = block.transactionCount.toString(),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        textAlign = TextAlign.Center
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(id = R.string.time),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
            Text(
                text = formattedDate,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(id = R.string.block_hash),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
            Text(
                text = block.hash,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1
            )
        }
    }
}