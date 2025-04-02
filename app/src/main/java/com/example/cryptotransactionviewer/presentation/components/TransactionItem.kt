package com.example.cryptotransactionviewer.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.cryptotransactionviewer.R
import com.example.cryptotransactionviewer.domain.model.Transaction
import com.example.cryptotransactionviewer.presentation.theme.BitcoinOrange
import com.example.cryptotransactionviewer.presentation.theme.TezosGreen
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun TransactionItem(
    transaction: Transaction,
    modifier: Modifier = Modifier
){
    val dateFormat = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())
    val formattedDate = dateFormat.format(Date(transaction.time * 1000))

    // Calculate total input and output values
    val totalInput = transaction.inputs.sumOf { it.value }
    val totalOutput = transaction.outputs.sumOf { it.value }

    // Determine if it's incoming or outgoing (simplified  for display)
    val isIncoming = totalOutput > totalInput

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Row(
            modifier= modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
//            Icon Box
            Box(
                modifier = modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(
                        if (isIncoming) Color.Green.copy(alpha = 0.1f)
                        else Color.Red.copy(alpha = 0.2f)
                    ),
                contentAlignment = Alignment.Center
            ){
                Icon(
                    imageVector = if (isIncoming) Icons.AutoMirrored.Filled.ArrowForward else Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = if (isIncoming) stringResource(id = R.string.incoming) else stringResource(
                        id = R.string.outgoing
                    ),
                    tint = if (isIncoming) TezosGreen else Color.Red
                )
            }

            Spacer(modifier = modifier.width(16.dp))

            Column(
                modifier = modifier.weight(1f)
            ) {
                // Text field shows Transaction hash (shortened)
                Text(
                    text = "Id: ${transaction.hash.take(8)}...${transaction.hash.takeLast(8)}",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = modifier.height(4.dp))

                // Transaction Date
                Text(
                    text = formattedDate,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
            Column(
                horizontalAlignment = Alignment.End
            ) {
                // Amount in BTC (simplified conversion for display)
                val amountInBtc =totalOutput.toDouble() / 100000000.0
                Text(
                    text = String.format(Locale.getDefault(),"%.8f BTC", amountInBtc),
                    style = MaterialTheme.typography.bodyMedium.copy( fontWeight = FontWeight.Bold, color = BitcoinOrange),
                )
                Spacer(modifier = Modifier.height(4.dp))
                // Fee (Simplified Conversion)
                val feeInBtc = transaction.fee.toDouble() / 100000000.0

                Text(
                    text = "Fee: ${String.format(Locale.getDefault(), "%.8f BTC", feeInBtc)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }

        }
    }
}