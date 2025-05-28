package com.example.debtgo.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.School
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.debtgo.data.model.Notification
import com.example.debtgo.data.model.NotificationType
import com.example.debtgo.ui.components.NotificationItem
import com.example.debtgo.ui.navigation.Screen

@Composable
fun HomeScreen(
    navController: NavController,
    onLogout: () -> Unit,
    isBasicPlan: Boolean = false,
    totalDebt: Double?,
    monthlyPayment: Double?,
    progress: Float,
    interestRate: Double,
    estimatedPayoffDate: String,
    remainingBalance: Double
)
{
    val notifications = remember {
        listOf(
            Notification(
                id = "1",
                icon = Icons.Default.CalendarToday,
                title = "Next Payment",
                message = "Your payment of /$300 is due in 3 days",
                time = "Today, 10:30 AM",
                type = NotificationType.REMINDER
            ),
            Notification(
                id = "2",
                icon = Icons.Default.Star,
                title = "Achievement Unlocked",
                message = "You've reached 50% debt reduction",
                time = "Yesterday, 3:45 PM",
                type = NotificationType.ACHIEVEMENT
            ),
            Notification(
                id = "3",
                icon = Icons.Default.Money,
                title = "Savings Opportunity",
                message = "You could save \$120 by refinancing your debt",
                time = "May 10",
                type = NotificationType.OFFER
            )
        )
    }

    val unreadCount = remember { notifications.count { !it.read } }

    Scaffold(
        topBar = {
            DebtGoTopBar(
            onLogout = onLogout,
            notificationCount = 0,
                onNotificationClick = { navController.navigate(Screen.NotificationPreferencesScreen.route) }
            )
        },
        bottomBar = {
            DebtGoBottomNavigation(navController)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .background(Color(0xFFF5F5FF)),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Header con imagen
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color(0xFF6200EE), Color(0xFF998EF7))
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Welcome to DebtGo",
                        style = MaterialTheme.typography.headlineLarge.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = "Your financial freedom journey",
                        style = MaterialTheme.typography.bodyLarge.copy(color = Color.White)
                    )
                }
            }

            // Tarjetas de métricas
            if (unreadCount > 0) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "Notifications",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            TextButton(onClick = { navController.navigate(Screen.NotificationPreferencesScreen.route)
                            }) {
                                Text("View All")
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            notifications.take(2).forEach { notification ->
                                NotificationItem(
                                    notification = notification,
                                    onClick = {}
                                )
                            }
                        }
                    }
                }
            }


            // Progreso de deuda
            DebtProgressCard(
                progress = progress,
                paid = totalDebt?.times(progress) ?: 0.0,
                remaining = totalDebt?.times(1 - progress) ?: 0.0
            )

            // Sección de Ayuda para el Pago (Visible solo en el plan Premium)
            if (!isBasicPlan) {
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        "Payment Assistance",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    PayoffProjectionCard( // Proyección detallada para el plan Premium
                        estimatedPayoffDate = estimatedPayoffDate, // Usa los datos proporcionados
                        remainingBalance = remainingBalance, // Usa los datos proporcionados
                        monthlyPayment = monthlyPayment ?: 0.0, // Usa los datos proporcionados con valor por defecto
                        modifier = Modifier.fillMaxWidth()
                    )
                    ActionButton(
                        icon = Icons.Default.Payment,
                        text = "Manage Payments",
                        color = Color(0xFF4CAF50)
                    ) {
                        navController.navigate(Screen.ManagePayments.route)
                    }
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            "Explore options to refinance or consolidate your debt for potential savings.",
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }

            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Acciones rápidas
                Text(
                    text = "Quick Actions",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    var showDialog by remember { mutableStateOf(false)}
                    ActionButton(
                        icon = Icons.Default.Add,
                        text = "Add Debt",
                        color = Color(0xFF6200EE)
                    ) { navController.navigate(Screen.AddDebt.route)
                        showDialog = true
                    }

                    if (showDialog) {
                        AlertDialog(
                            onDismissRequest = { showDialog = false},
                            title = { Text("Add New Debt") },
                            text = {},
                            confirmButton = {
                                Button(onClick = { showDialog = false }) {
                                    Text("Save")
                                }
                            },
                              dismissButton = {
                                  Button(onClick = { showDialog = false}) {
                                     Text("Cancel")
                                  }
                              }
                        )
                    }
                }
            }

            // Próximos vencimientos
            NextPaymentsSection(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                payments = listOf(
                    Payment("Credit Card", 120.0, "May 15"),
                    Payment("Student Loan", 230.0, "May 20")
                )
            )
        }
    }
}

@Composable
fun PayoffProjectionCard(
    estimatedPayoffDate: String,
    remainingBalance: Double,
    monthlyPayment: Double,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "Payment Projection",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Divider(modifier = Modifier.padding(bottom = 4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Estimated Payment Date:", fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyLarge)
                Text(estimatedPayoffDate, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Remaining Balance:", fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyLarge)
                Text("$${"%,.2f".format(remainingBalance)}", fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyLarge)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Current Monthly Payment:", fontWeight = FontWeight.Medium)
                Text("$${"%,.2f".format(monthlyPayment)}", fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}

@Composable
fun ActionButton(
    icon: ImageVector,
    text: String,
    color: Color,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = color,
            contentColor = Color.White
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(icon, contentDescription = text)
            Text(text)
        }
    }
}

@Composable
fun NextPaymentsSection(payments: List<Payment>, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                "Upcoming Payments",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            payments.forEach { payment ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(payment.name)
                    Text("$${"%,.2f".format(payment.amount)}")
                    Text(payment.dueDate)
                }
                if (payment != payments.last()) {
                    Divider(modifier = Modifier.padding(vertical = 4.dp))
                }
            }
        }
    }
}

@Composable
fun DebtGoBottomNavigation(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = currentRoute == Screen.Home.route,
            onClick = { navController.navigate(Screen.Home.route) }
        )

        // Nueva pestaña Educación
        NavigationBarItem(
            icon = { Icon(Icons.Outlined.School, contentDescription = "Educación") },
            label = { Text("Educación") },
            selected = currentRoute == Screen.Education.route,
            onClick = { navController.navigate(Screen.Education.route) }
        )

        NavigationBarItem(
            icon = { Icon(Icons.Default.AccountCircle, contentDescription = "Profile") },
            label = { Text("Profile") },
            selected = currentRoute == Screen.Profile.route,
            onClick = { navController.navigate(Screen.Profile.route) }
        )
    }
}

// Componentes reutilizables:

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DebtGoTopBar(
    onLogout: () -> Unit,
    notificationCount: Int,
    onNotificationClick: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = { Text("DebtGo") },
        actions = {
            Box(contentAlignment = Alignment.TopEnd) { // Usa un Box para apilar el IconButton y el Badge
                IconButton(onClick = onNotificationClick) {
                    Icon(
                        imageVector = Icons.Outlined.Notifications,
                        contentDescription = "Notifications",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
                if (notificationCount > 0) {
                    Badge(
                        modifier = Modifier
                            .offset(x = 8.dp, y = (-8).dp), // Ajusta el offset relativo a TopEnd del Box
                        containerColor = Color(0xFFE53E3E)
                    ) {
                        Text(notificationCount.toString())
                    }
                }
            }
            IconButton(onClick = onLogout) {
                Icon(
                    imageVector = Icons.Default.Logout,
                    contentDescription = "Logout",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    )
}

@Composable
fun MetricCard(
    title: String,
    value: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                modifier = Modifier.size(28.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun DebtProgressCard(progress: Float, paid: Double, remaining: Double) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Debt Progress",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp),
                color = Color(0xFF6200EE),
                trackColor = Color(0xFFE0E0E0)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Paid", style = MaterialTheme.typography.labelSmall)
                    Text(
                        "$${"%,.2f".format(paid)}",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text("Remaining", style = MaterialTheme.typography.labelSmall)
                    Text(
                        "$${"%,.2f".format(remaining)}",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
    }
}

// Modelo de datos para pagos
data class Payment(val name: String, val amount: Double, val dueDate: String)