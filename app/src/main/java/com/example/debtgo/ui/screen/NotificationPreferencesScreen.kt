package com.example.debtgo.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationPreferencesScreen(
    navController: NavController,
    onSavePreferences: (paymentReminders: Boolean, actionConfirmations: Boolean, accountAlerts: Boolean) -> Unit
) {
    var paymentRemindersEnabled by remember { mutableStateOf(true) }
    var actionConfirmationsEnabled by remember { mutableStateOf(true) }
    var accountAlertsEnabled by remember { mutableStateOf(true) }
    var savingPreferences by remember { mutableStateOf(false) }
    var saveSuccessful by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Notification Preferences", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = {
                        // Utiliza popBackStack para navegar a la pantalla anterior
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            Button(
                onClick = {
                    savingPreferences = true
                    saveSuccessful = false
                    coroutineScope.launch {
                        delay(1500)
                        onSavePreferences(paymentRemindersEnabled, actionConfirmationsEnabled, accountAlertsEnabled)
                        savingPreferences = false
                        saveSuccessful = true
                        delay(2000)
                        saveSuccessful = false
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                enabled = !savingPreferences
            ) {
                Text(if (savingPreferences) "Saving..." else "Save Notification Preferences")
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .alpha(if (savingPreferences || saveSuccessful) 0.3f else 1f)
            ) {
                Text(
                    "Customize how and when you'd like to receive notifications.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Text("Notification Types", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(bottom = 8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Payment Reminders", style = MaterialTheme.typography.bodyLarge)
                    Switch(
                        checked = paymentRemindersEnabled,
                        onCheckedChange = { paymentRemindersEnabled = it }
                    )
                }
                Text("Get notified before your payments are due.", style = MaterialTheme.typography.bodySmall, color = Color.Gray, modifier = Modifier.padding(bottom = 8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Action Confirmations", style = MaterialTheme.typography.bodyLarge)
                    Switch(
                        checked = actionConfirmationsEnabled,
                        onCheckedChange = { actionConfirmationsEnabled = it }
                    )
                }
                Text("Receive alerts when you add a debt or make a payment.", style = MaterialTheme.typography.bodySmall, color = Color.Gray, modifier = Modifier.padding(bottom = 16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Account Alerts", style = MaterialTheme.typography.bodyLarge)
                    Switch(
                        checked = accountAlertsEnabled,
                        onCheckedChange = { accountAlertsEnabled = it }
                    )
                }
                Text("Be notified about important updates to your account.", style = MaterialTheme.typography.bodySmall, color = Color.Gray, modifier = Modifier.padding(bottom = 16.dp))

                Text("Notification Channels", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(bottom = 8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    var emailEnabled by remember { mutableStateOf(true) }
                    Checkbox(checked = emailEnabled, onCheckedChange = { emailEnabled = it })
                    Text("Email", style = MaterialTheme.typography.bodyLarge)
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    var smsEnabled by remember { mutableStateOf(false) }
                    Checkbox(checked = smsEnabled, onCheckedChange = { smsEnabled = it })
                    Text("SMS", style = MaterialTheme.typography.bodyLarge)
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    var pushEnabled by remember { mutableStateOf(true) }
                    Checkbox(checked = pushEnabled, onCheckedChange = { pushEnabled = it })
                    Text("Push Notification", style = MaterialTheme.typography.bodyLarge)
                }
            }

            if (savingPreferences) {
                Card(
                    modifier = Modifier.size(120.dp),
                    shape = CircleShape,
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimaryContainer)
                    }
                }
            } else if (saveSuccessful) {
                Card(
                    modifier = Modifier.size(120.dp),
                    shape = CircleShape,
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF4CAF50).copy(alpha = 0.8f)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Filled.Check,
                            contentDescription = "Saved",
                            tint = Color.White,
                            modifier = Modifier.size(60.dp)
                        )
                    }
                }
            }

            // Mensaje de "Saving..." o "Saved successfully" centrado debajo del indicador
            if (savingPreferences) {
                Text(
                    "Saving...",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(top = 16.dp)
                )
            } else if (saveSuccessful) {
                Text(
                    "Your preferences have been saved successfully",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4CAF50),
                    modifier = Modifier
                        .padding(top = 16.dp)
                )
            }
        }
    }
}