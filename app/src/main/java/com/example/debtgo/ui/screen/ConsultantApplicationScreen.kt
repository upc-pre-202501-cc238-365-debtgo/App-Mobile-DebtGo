package com.example.debtgo.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConsultantApplicationScreen(navController: NavController) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var experience by remember { mutableStateOf("") }
    var specialties by remember { mutableStateOf("") }
    var certifications by remember { mutableStateOf("") }
    var agreedToTerms by remember { mutableStateOf(false) } // Estado para el checkbox

    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Become a Certified Consultant") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Outlined.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar {
                Button(
                    onClick = {
                        navController.popBackStack()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = agreedToTerms && fullName.isNotBlank() && email.isNotBlank(), // Validación
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4A90E2),
                        contentColor = Color.White,
                        disabledContainerColor = Color(0xFFA0AEC0), // Color cuando está deshabilitado
                        disabledContentColor = Color.White
                    )
                ) {
                    Text("Submit Application", fontSize = 16.sp)
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            // Información sobre el programa
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFEBF5FF))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "Consultant Certification Program",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2C5282)
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Our certification program ensures you have the knowledge and skills to provide " +
                                "high-quality financial advice to our clients.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Benefits:",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text("- Official certification badge")
                    Text("- Access to exclusive resources")
                    Text("- Higher earning potential")
                    Text("- Professional network")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Formulario de aplicación
            Text(
                "Personal Information",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = fullName,
                onValueChange = { fullName = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Full Name") },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Email") },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Phone Number") },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                "Professional Background",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = experience,
                onValueChange = { experience = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Years of Financial Experience") }
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = specialties,
                onValueChange = { specialties = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Areas of Specialization") }
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = certifications,
                onValueChange = { certifications = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Current Certifications (if any)") }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Requisitos y condiciones
            Text(
                "Requirements",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
            )
            Text("- Complete all advanced courses")
            Text("- Pass the certification exam")
            Text("- Agree to our code of ethics")
            Text("- Maintain active status with continuing education")

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Checkbox(
                    checked = agreedToTerms,
                    onCheckedChange = { agreedToTerms = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color(0xFF4A90E2),
                        uncheckedColor = Color(0xFF718096)
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "I agree to the terms and conditions of the certification program",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}