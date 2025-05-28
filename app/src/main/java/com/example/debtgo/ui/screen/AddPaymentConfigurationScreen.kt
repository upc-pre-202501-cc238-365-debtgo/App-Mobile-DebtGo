package com.example.debtgo.ui.screen

import PaymentViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.debtgo.data.model.PaymentConfiguration

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPaymentConfigurationScreen(
    navController: NavController,
    viewModel: PaymentViewModel = viewModel()
) {
    var showSuccessDialog by remember { mutableStateOf(false) }

    // Mostrar diálogo de éxito
    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { showSuccessDialog = false },
            title = { Text("Success") },
            text = { Text("Payment settings saved successfully") },
            confirmButton = {
                Button(
                    onClick = {
                        showSuccessDialog = false
                        navController.popBackStack()
                    }
                ) {
                    Text("Accept")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Payment Configuration") },
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
                    "Set up a new payment configuration for your debts.",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.fillMaxWidth()
                )

                var paymentName by remember { mutableStateOf("") }
                var selectedDebt by remember { mutableStateOf("Credit Card") }
                var paymentAmount by remember { mutableStateOf("") }
                var selectedFrequency by remember { mutableStateOf("Monthly") }
                var showFrequencyDropdown by remember { mutableStateOf(false) }
                val frequencyOptions = listOf("Monthly", "Biweekly", "Weekly", "Custom")

                // Campo para el nombre del pago
                OutlinedTextField(
                    value = paymentName,
                    onValueChange = { paymentName = it },
                    label = { Text("Payment Name*") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = paymentName.isBlank()
                )

                // Selector de deuda (simplificado)
                OutlinedTextField(
                    value = selectedDebt,
                    onValueChange = { selectedDebt = it },
                    label = { Text("Debt to Pay") },
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = {}) {
                            Icon(Icons.Filled.ArrowDropDown, contentDescription = "Select Debt")
                        }
                    }
                )

                // Campo para el monto del pago
                OutlinedTextField(
                    value = paymentAmount,
                    onValueChange = { paymentAmount = it },
                    label = { Text("Payment Amount*") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = paymentAmount.isBlank(),
                    supportingText = {
                        if (paymentAmount.isBlank()) {
                            Text("This field is required")
                        }
                    }
                )

                // Selector de frecuencia
                Box(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = selectedFrequency,
                        onValueChange = {},
                        label = { Text("Payment Frequency*") },
                        modifier = Modifier.fillMaxWidth(),
                        readOnly = true,
                        trailingIcon = {
                            IconButton(onClick = { showFrequencyDropdown = true }) {
                                Icon(Icons.Filled.ArrowDropDown, contentDescription = "Select Frequency")
                            }
                        }
                    )

                    DropdownMenu(
                        expanded = showFrequencyDropdown,
                        onDismissRequest = { showFrequencyDropdown = false }
                    ) {
                        frequencyOptions.forEach { frequency ->
                            DropdownMenuItem(
                                text = { Text(frequency) },
                                onClick = {
                                    selectedFrequency = frequency
                                    showFrequencyDropdown = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                // Botón de guardar
                Button(
                    onClick = {
                        when {
                            paymentName.isBlank() -> {
                            }
                            paymentAmount.isBlank() -> {
                            }
                            else -> {
                                try {
                                    val amount = paymentAmount.toDouble()
                                    if (amount > 0) {
                                        val newConfig = PaymentConfiguration(
                                            debtName = paymentName,
                                            amount = amount,
                                            frequency = selectedFrequency
                                        )
                                        viewModel.addPaymentConfiguration(newConfig)
                                        showSuccessDialog = true
                                    }
                                } catch (e: NumberFormatException) {
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    enabled = paymentName.isNotBlank() && paymentAmount.isNotBlank()
                ) {
                    Text("Save Payment Configuration", style = MaterialTheme.typography.titleMedium)
                }
            }
        }
    )
}