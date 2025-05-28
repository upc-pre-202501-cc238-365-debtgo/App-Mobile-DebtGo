package com.example.debtgo.ui.screen

import PaymentViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.debtgo.ui.navigation.Screen

// Modelo de datos simulado para una configuración de pago
data class PaymentConfiguration(val debtName: String, val amount: Double, val frequency: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManagePaymentsScreen(navController: NavController,
                         viewModel: PaymentViewModel = viewModel()) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Manage Payments") },
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
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    "Here you can view and manage your saved payment configurations.",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.fillMaxWidth()
                )

                // Lista simulada de configuraciones de pago
                val paymentConfigurations = remember {
                    listOf(
                        PaymentConfiguration("Credit Card", 150.0, "Monthly"),
                        PaymentConfiguration("Student Loan", 250.0, "Monthly"),
                        PaymentConfiguration("Car Loan", 300.0, "Monthly")
                    )
                }

                if (paymentConfigurations.isNotEmpty()) {
                    Text(
                        "Current Payment Configurations:",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
                    )
                    paymentConfigurations.forEach { config ->
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text(config.debtName, style = MaterialTheme.typography.titleSmall)
                                    Text("$${"%,.2f".format(config.amount)}", style = MaterialTheme.typography.bodyMedium)
                                }
                                Text(config.frequency, style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                } else {
                    Text("No payment configurations saved yet.", style = MaterialTheme.typography.bodyMedium)
                }

                Spacer(modifier = Modifier.weight(1f)) // Empuja el botón hacia abajo

                Button(
                    onClick = { navController.navigate(Screen.AddPaymentConfiguration.route) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Add Payment Configuration", style = MaterialTheme.typography.titleMedium)
                }
            }
        }
    )
}