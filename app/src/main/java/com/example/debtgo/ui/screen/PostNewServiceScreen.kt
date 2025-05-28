package com.example.debtgo.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.debtgo.viewmodel.analysis.ServiceViewModel

@Composable
fun PostNewServiceScreen(
    navController: NavController,
    viewModel: ServiceViewModel = viewModel()
) {
    var priceInput by remember { mutableStateOf("") }
    var showDescDialog by remember { mutableStateOf(false) }
    var showPriceDialog by remember { mutableStateOf(false) }
    var tempDescription by remember { mutableStateOf(viewModel.description) }
    var priceConfirmed by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = 24.dp, vertical = 46.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Text(
                "Post new service",
                style = MaterialTheme.typography.titleLarge
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Add title
        Text("Add a title", style = MaterialTheme.typography.labelMedium)
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = viewModel.title,
            onValueChange = { viewModel.title = it },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            placeholder = { Text("Add a title") }
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Add description
        Text("Add a description", style = MaterialTheme.typography.labelMedium)
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFE0E0E0), RoundedCornerShape(8.dp))
                .clickable { showDescDialog = true }
                .padding(12.dp)
        ) {
            Text(
                text = if (viewModel.description.isBlank()) "+ Add a description" else viewModel.description,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = if (viewModel.description.isBlank()) Color.Gray else Color.Black,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Price Options section
        Text("Price Options", style = MaterialTheme.typography.labelMedium)
        Spacer(modifier = Modifier.height(4.dp))
        Button(
            onClick = { showPriceDialog = true },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF333333))
        ) {
            Text(
                text = if (priceConfirmed) "✏ Edit price options" else "+ Add price options",
                color = Color.White
            )
        }

        // Summary card shown only if prices exist
        if (viewModel.prices.isNotEmpty()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF424242)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Name: ${viewModel.title.ifBlank { "N/A" }}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White
                    )
                    Text(
                        text = "Price: S/. 00.00",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    viewModel.prices.forEach { detail ->
                        Text(
                            text = "• $detail",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Add file box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(Color(0xFFE0E0E0), shape = RoundedCornerShape(12.dp))
                .clickable { viewModel.fileName = "archivo.pdf" },
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    Icons.Default.AttachFile,
                    contentDescription = "Add a file",
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text("Add a file", style = MaterialTheme.typography.bodyMedium)
            }
        }

        Spacer(modifier = Modifier.height(60.dp))
        // Final confirm button
        Button(
            onClick = {
                // Save or submit the full service here
                navController.popBackStack()
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(150.dp)
                .height(48.dp),
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF333333))
        ) {
            Text("Confirm", color = Color.White)
        }
    }

    // Description dialog
    if (showDescDialog) {
        AlertDialog(
            onDismissRequest = { showDescDialog = false },
            title = { Text("Add a description") },
            text = {
                OutlinedTextField(
                    value = tempDescription,
                    onValueChange = { tempDescription = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.description = tempDescription
                    showDescDialog = false
                }) {
                    Text("Add")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDescDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    // Price options dialog
    if (showPriceDialog) {
        AlertDialog(
            onDismissRequest = { showPriceDialog = false },
            title = { Text("Add a price option") },
            text = {
                OutlinedTextField(
                    value = priceInput,
                    onValueChange = { priceInput = it },
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    if (priceInput.isNotBlank()) {
                        viewModel.addPriceOption(priceInput)
                        priceInput = ""
                        priceConfirmed = true // mark as confirmed
                    }
                    showPriceDialog = false
                }) {
                    Text("Add")
                }
            },
            dismissButton = {
                TextButton(onClick = { showPriceDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}