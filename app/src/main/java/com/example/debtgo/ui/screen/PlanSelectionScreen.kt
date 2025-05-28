package com.example.debtgo.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PlanSelectionScreen(
    onBasicPlanSelected: () -> Unit,
    onPremiumPlanSelected: () -> Unit,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "Select a plan",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF333333)
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Basic Plan Card
        PlanCard(
            title = "Basic",
            price = "S/80 /month",
            features = listOf(
                "Basic debt management tools ",
                "Income and expense tracking ",
                "Budget creation tools ",
                "Access to standard financial education materials "
            ),
            buttonText = "Start",
            onClick = onBasicPlanSelected,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Premium Plan Card
        PlanCard(
            title = "Premium",
            price = "S/150 /month",
            features = listOf(
                "All Basic Plan features ",
                "Personalized financial consulting ",
                "Priority customer support"
            ),
            buttonText = "Start",
            onClick = onPremiumPlanSelected,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun PlanCard(
    title: String,
    price: String,
    features: List<String>,
    buttonText: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF6200EE)
            )

            Text(
                text = price,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )

            Divider(color = Color(0xFFEEEEEE), thickness = 1.dp)

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                features.forEach { feature ->
                    Text(
                        text = "• $feature",
                        fontSize = 14.sp,
                        color = Color(0xFF666666)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6200EE),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = buttonText,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}