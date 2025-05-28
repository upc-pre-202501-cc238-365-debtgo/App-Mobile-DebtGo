package com.example.debtgo.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDebtScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add New Debt") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 24.dp, vertical = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Text(
                    "Here you have some information about Add New Debt, " +
                    "please enter the details of the debt you want to add.",
                    style = MaterialTheme.typography.bodyLarge
                )

                var debtName by remember { mutableStateOf("") }
                var debtAmount by remember { mutableStateOf("") }
                var interestRate by remember { mutableStateOf("") }
                var minimumPayment by remember { mutableStateOf("") }
                var notes by remember { mutableStateOf("") } // Nuevo campo para notas

                OutlinedTextField(
                    value = debtName,
                    onValueChange = { debtName = it },
                    label = { Text("Debt Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = debtAmount,
                    onValueChange = { debtAmount = it },
                    label = { Text("Amount") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = interestRate,
                    onValueChange = { interestRate = it },
                    label = { Text("Interest Rate (%)") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = minimumPayment,
                    onValueChange = { minimumPayment = it },
                    label = { Text("Minimum Payment") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Notes (Optional)") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        navController.popBackStack()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Add Debt", style = MaterialTheme.typography.titleMedium)
                }
            }
        }
    )
}