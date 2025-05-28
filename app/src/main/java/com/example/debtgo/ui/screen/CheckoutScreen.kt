package com.example.debtgo.ui.screen

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.debtgo.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    navController: NavController,
    selectedPlan: String,
    planPrice: String,
    planFeatures: List<String>,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Proceed to checkout") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back to Plans")
                    }
                }
            )
        }
    ) { paddingValues ->
        var isPaymentLoading by remember { mutableStateOf(false) }
        var isPaymentSuccessful by remember { mutableStateOf(false) }

        var cardOwnerName by remember { mutableStateOf("") }
        var cardNumber by remember { mutableStateOf("") }
        var expiryDate by remember { mutableStateOf("") }
        var securityCode by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }

        val isCardOwnerNameValid = cardOwnerName.isNotBlank()
        val isCardNumberValid = cardNumber.isNotBlank()
        val isExpiryDateValid = expiryDate.isNotBlank()
        val isSecurityCodeValid = securityCode.isNotBlank()
        val isEmailValid = email.isNotBlank()

        val isPaymentDetailsValid = remember(
            isCardOwnerNameValid,
            isCardNumberValid,
            isExpiryDateValid,
            isSecurityCodeValid,
            isEmailValid
        ) {
            isCardOwnerNameValid &&
                    isCardNumberValid &&
                    isExpiryDateValid &&
                    isSecurityCodeValid &&
                    isEmailValid
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "You have selected the",
                    fontSize = 16.sp,
                    color = Color(0xFF666666)
                )

                Text(
                    text = selectedPlan,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF6200EE)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFFF5F5F5),
                    tonalElevation = 2.dp
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Order Summary",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF333333)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Plan:", fontWeight = FontWeight.Medium)
                            Text(selectedPlan)
                        }
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Price:", fontWeight = FontWeight.Medium)
                            Text(planPrice, fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Features:",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF333333)
                        )
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            planFeatures.forEach { feature ->
                                Text("• $feature", fontSize = 14.sp, color = Color(0xFF666666))
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Payment Details",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF333333),
                    modifier = Modifier.fillMaxWidth().align(Alignment.Start)
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = cardOwnerName,
                    onValueChange = { cardOwnerName = it },
                    label = { Text("Card owner name:") },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("e.g., John Doe") }
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = cardNumber,
                    onValueChange = { cardNumber = it },
                    label = { Text("Card number:") },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("e.g., 1234-5678-9012-3456") }
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = expiryDate,
                        onValueChange = { expiryDate = it },
                        label = { Text("Expiry date:") },
                        placeholder = { Text("MM / AA") },
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = securityCode,
                        onValueChange = { securityCode = it },
                        label = { Text("Security code:") },
                        placeholder = { Text("CVCH") },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email:") },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("e.g., john.doe@example.com") }
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        if (isPaymentDetailsValid && !isPaymentLoading) {
                            isPaymentLoading = true
                            Handler(Looper.getMainLooper()).postDelayed({
                                isPaymentLoading = false
                                isPaymentSuccessful = true
                                Handler(Looper.getMainLooper()).postDelayed({
                                    isPaymentSuccessful = false
                                    navController.navigate(Screen.Home.route) {
                                        popUpTo(navController.graph.startDestinationId) {
                                            inclusive = true
                                        }
                                        launchSingleTop = true
                                    }
                                }, 2000)
                            }, 3000)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isPaymentDetailsValid) Color(0xFF6200EE) else Color.Gray,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(8.dp),
                    enabled = isPaymentDetailsValid && !isPaymentLoading
                ) {
                    Text(
                        text = if (isPaymentLoading) "Processing..." else "Confirm",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
            }

            if (isPaymentLoading || isPaymentSuccessful) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.6f)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (isPaymentLoading) {
                            CircularProgressIndicator(
                                color = Color(0xFF6200EE),
                                modifier = Modifier.size(58.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Please wait", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 22.sp)
                            Text("Payment in progress", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 22.sp)
                        }

                        if (isPaymentSuccessful) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                CircularCheckIcon() // Usamos el composable CircularCheckIcon aquí
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    "Successful Purchase!",
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF4CAF50),
                                    fontSize = 24.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CircularCheckIcon() {
    val size = 72.dp
    val strokeWidth = 6.dp
    val checkColor = Color(0xFF4CAF50)
    val backgroundColor = Color(0xFFE8F5E9)

    androidx.compose.foundation.Canvas(
        modifier = Modifier.size(size)
    ) {
        val center = Offset(size.toPx() / 2f, size.toPx() / 2f)
        val radius = size.toPx() / 2f - strokeWidth.toPx() / 2f
        val checkSize = size.toPx() * 0.4f
        val checkStrokeWidth = strokeWidth.toPx()

        // Dibuja el círculo de fondo
        drawCircle(
            color = backgroundColor,
            radius = radius,
            center = center
        )

        // Dibuja el checkmark
        val startX = center.x - checkSize / 3
        val startY = center.y - checkSize / 6
        val midX = center.x
        val midY = center.y + checkSize / 3
        val endX = center.x + checkSize / 2
        val endY = center.y - checkSize / 3

        drawLine(
            color = checkColor,
            start = Offset(startX, startY),
            end = Offset(midX, midY),
            strokeWidth = checkStrokeWidth,
            cap = StrokeCap.Round
        )
        drawLine(
            color = checkColor,
            start = Offset(midX, midY),
            end = Offset(endX, endY),
            strokeWidth = checkStrokeWidth,
            cap = StrokeCap.Round
        )

        // Dibuja el borde del círculo (opcional)
        drawCircle(
            color = checkColor,
            radius = radius,
            center = center,
            style = Stroke(width = strokeWidth.toPx())
        )
    }
}

fun Dp.toPx() = this.value * 3f