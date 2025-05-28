package com.example.debtgo.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RegisterScreen(
    onRegisterClick: (name: String, email: String, role: String, password: String) -> Unit,
    onNavigateToLogin: () -> Unit,
    onNavigateToPlanSelection: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var acceptTerms by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }

    // Validaciones en tiempo real
    val isEmailValid = remember(email) {
        email.isNotBlank() && email.contains("@") && email.contains(".")
    }
    val hasNumber = password.any { it.isDigit() }
    val hasMinLength = password.length >= 8
    val isPasswordValid = hasNumber && hasMinLength

    // Validación completa del formulario
    val isFormValid = remember(name, email, role, password, acceptTerms) {
        name.isNotBlank() &&
                email.isNotBlank() &&
                role.isNotBlank() &&
                password.isNotBlank() &&
                acceptTerms &&
                isEmailValid &&
                isPasswordValid
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Fondo dividido (50% blanco arriba - 50% morado abajo)
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .weight(0.4f)
                    .background(Color.White)
            )
            Box(
                modifier = Modifier
                    .weight(0.6f)
                    .background(Color(0xFF998EF7))
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(35.dp))

            // Encabezado
            Text(
                text = "Welcome to DebtGo",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333))

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Take control of your debts",
                fontSize = 16.sp,
                color = Color(0xFF666666))

            Spacer(modifier = Modifier.height(32.dp))

            // Título del formulario
            Text(
                text = "Register",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333),
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Campos del formulario
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de Email con validación
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                isError = email.isNotBlank() && !isEmailValid,
                supportingText = {
                    if (email.isNotBlank() && !isEmailValid) {
                        Text("Please enter a valid email address")
                    }
                },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    errorContainerColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = role,
                onValueChange = { role = it },
                label = { Text("Role") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de Password con validación
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = if (passwordVisible) VisualTransformation.None
                else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                isError = password.isNotBlank() && !isPasswordValid,
                supportingText = {
                    if (password.isNotBlank() && !isPasswordValid) {
                        Text("Password doesn't meet requirements")
                    }
                },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Filled.Visibility
                            else Icons.Filled.VisibilityOff,
                            contentDescription = if (passwordVisible) "Hide password"
                            else "Show password"
                        )
                    }
                },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    errorContainerColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Requisitos de contraseña con feedback visual
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "• At least one number",
                    fontSize = 14.sp,
                    color = if (!hasNumber) Color(0xFFD32F2F) else Color(0xFF4CAF50) // Rojo si no cumple, verde si cumple
                )
                Text(
                    text = "• At least 8 characters",
                    fontSize = 14.sp,
                    color = if (!hasMinLength) Color(0xFFD32F2F) else Color(0xFF4CAF50)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Checkbox de términos
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = acceptTerms,
                    onCheckedChange = { acceptTerms = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color(0xFF6200EE)
                    )
                )
                Text(
                    text = "Accept the Terms and Conditions and Privacy Policy",
                    fontSize = 14.sp,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Botón Register - solo habilitado cuando todo es válido
            Button(
                onClick = { onRegisterClick(name, email, role, password) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isFormValid) Color(0xFF6200EE) else Color(0xFFCCCCCC),
                    disabledContainerColor = Color(0xFFCCCCCC)
                ),
                enabled = isFormValid
            ) {
                Text(
                    text = "Register",
                    fontSize = 16.sp,
                    color = if (isFormValid) Color.White else Color(0xFF999999)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Enlace a login
            TextButton(onClick = onNavigateToLogin) {
                Text(
                    text = "Already have an account? Log in",
                    color = Color(0xFF6200EE),
                    fontSize = 14.sp
                )
            }
        }
    }
}