package com.example.debtgo.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
fun LoginScreen(
    onLoginClick: (email: String, password: String) -> Unit,
    onNavigateToRegister: () -> Unit,
    onNavigateToPlanSelection: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }

    // Validación del email (debe contener @ y .)
    val isEmailValid = remember(email) {
        email.isNotBlank() && email.contains("@") && email.contains(".")
    }

    // Validación del password (mínimo 6 caracteres)
    val isPasswordValid = password.length >= 6

    // El formulario es válido solo cuando ambos campos son válidos
    val isFormValid = isEmailValid && isPasswordValid && rememberMe

    Box(modifier = Modifier.fillMaxSize()) {
        // Fondo dividido (40% blanco arriba - 60% morado abajo)
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
                color = Color(0xFF333333)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Manage your debts efficiently",
                fontSize = 16.sp,
                color = Color(0xFF666666))

            Spacer(modifier = Modifier.height(32.dp))

            // Título del formulario
            Text(
                text = "Login",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333),
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Campo de Email con validación
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true,
                isError = email.isNotBlank() && !isEmailValid,
                supportingText = {
                    if (email.isNotBlank() && !isEmailValid) {
                        Text("Please enter a valid email")
                    }
                },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    errorContainerColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de Password con validación
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                isError = password.isNotBlank() && !isPasswordValid,
                supportingText = {
                    if (password.isNotBlank() && !isPasswordValid) {
                        Text("Password must be at least 6 characters")
                    }
                },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            contentDescription = if (passwordVisible) "Hide password" else "Show password"
                        )
                    }
                },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    errorContainerColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Recordar usuario y olvidé contraseña
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = rememberMe,
                        onCheckedChange = { rememberMe = it },
                        colors = CheckboxDefaults.colors(
                            checkedColor = Color(0xFF6200EE)
                        )
                    )
                    Text(
                        "Remember me",
                        fontSize = 14.sp,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                TextButton(onClick = {}) {
                    Text(
                        "Forgot password?",
                        fontSize = 14.sp,
                        color = Color(0xFF6200EE)
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Botón Login - solo habilitado cuando el formulario es válido
            Button(
                onClick = {
                    if (isFormValid) {
                        onLoginClick(email, password)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isFormValid) Color(0xFF6200EE) else Color(0xFFCCCCCC),
                    disabledContainerColor = Color(0xFFCCCCCC)
                ),
                enabled = isFormValid,
                shape = RoundedCornerShape(8.dp) // Bordes redondeados consistentes
            ) {
                Text(
                    text = "Log In",
                    fontSize = 16.sp,
                    color = if (isFormValid) Color.White else Color(0xFF999999),
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Enlace a registro
            TextButton(onClick = onNavigateToRegister) {
                Text(
                    text = "Don't have an account? Register",
                    color = Color(0xFF6200EE),
                    fontSize = 14.sp
                )
            }
        }
    }
}