package com.example.debtgo.ui.screen

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.debtgo.data.model.Course
import com.example.debtgo.data.model.Opportunity
import com.example.debtgo.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EducationScreen(navController: NavController) {
    val courses = remember {
        mutableStateListOf(
            Course("Debt Reduction: Master Your Finances", 65, Icons.Outlined.MoneyOff, true, "Learn effective strategies to get out of debt and build a strong financial future."),
            Course("Basic Investments: Make Your Money Grow", 30, Icons.Outlined.TrendingUp, false, "Discover the fundamental principles of investing and how to start generating returns."),
            Course("Smart Financial Planning", 0, Icons.Outlined.CalendarMonth, false, "Create a personalized financial plan that helps you achieve your short-, medium-, and long-term goals.")
        )
    }

    fun completeCourse(title: String) {
        val index = courses.indexOfFirst { it.title == title }
        if (index != -1) {
            courses[index] = courses[index].copy(progress = 100)
        }
    }

    val opportunities = remember {
        listOf(
            Opportunity("Personalized Budget Mentoring", "Earn up to S/120 per session", Icons.Outlined.School, "Share your knowledge and guide others to take control of their finances."),
            Opportunity("Advanced Investment Workshop", "Earn S/200 + attractive commissions", Icons.Outlined.Business, "Facilitates dynamic workshops and helps others understand more complex investment strategies."),
            Opportunity("Creating Financial Content", "Variable income depending on the project", Icons.Outlined.Edit, "Create articles, guides, and resources for our community and earn revenue from your expertise.")
        )
    }

    // Estados para controlar los diálogos
    var showCourseDetailsDialog by remember { mutableStateOf<Course?>(null) }
    var showOpportunityDetailsDialog by remember { mutableStateOf<Opportunity?>(null) }
    var showConsultantConfirmationDialog by remember { mutableStateOf(false) }

    // Diálogo para detalles del curso
    showCourseDetailsDialog?.let { course ->
        AlertDialog(
            onDismissRequest = { showCourseDetailsDialog = null },
            title = { Text(course.title) },
            text = {
                Column {
                    Text(course.description)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Progress: ${course.progress}%",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = if (course.progress == 0) Color.Red else Color.Green
                        ))
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        showCourseDetailsDialog = null
                        navController.navigate("course_detail/${course.title}")
                    }
                ) {
                    Text("Go to Course")
                }
            },
            dismissButton = {
                TextButton(onClick = { showCourseDetailsDialog = null }) {
                    Text("Close")
                }
            }
        )
    }

    // Diálogo para detalles de oportunidad
    showOpportunityDetailsDialog?.let { opportunity ->
        AlertDialog(
            onDismissRequest = { showOpportunityDetailsDialog = null },
            title = { Text(opportunity.title) },
            text = {
                Column {
                    Text(opportunity.description)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(opportunity.earnings,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = Color(0xFF38A169),
                            fontWeight = FontWeight.Bold
                        ))
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        showOpportunityDetailsDialog = null
                        navController.navigate("opportunity_apply/${opportunity.title}")
                    }
                ) {
                    Text("Apply Now")
                }
            },
            dismissButton = {
                TextButton(onClick = { showOpportunityDetailsDialog = null }) {
                    Text("Close")
                }
            }
        )
    }

    // Diálogo de confirmación para consultor
    if (showConsultantConfirmationDialog) {
        AlertDialog(
            onDismissRequest = { showConsultantConfirmationDialog = false },
            title = { Text("Become a Certified Consultant") },
            text = { Text("Are you sure you want to start the certification process? This will require completing our advanced courses and passing an evaluation.") },
            confirmButton = {
                Button(
                    onClick = {
                        showConsultantConfirmationDialog = false
                        navController.navigate("consultant_application")
                    }
                ) {
                    Text("Continue")
                }
            },
            dismissButton = {
                TextButton(onClick = { showConsultantConfirmationDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "My Financial Education Center",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF2D3748)
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Education.route) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBack,
                            contentDescription = "Return",
                            tint = Color(0xFF4A90E2)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black
                )
            )
        },
        bottomBar = {
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .background(Color(0xFFF8FAFC))
        ) {
            // Sección de Certificaciones
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(vertical = 24.dp, horizontal = 16.dp)
                ) {
                    Text(
                        "GET CERTIFIED AND CONSULT",
                        style = MaterialTheme.typography.labelLarge.copy(
                            color = Color(0xFF4A90E2),
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Boost your career with our certified courses.",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = Color(0xFF718096)
                        )
                    )
                }
            }

            // Cursos
            items(courses) { course ->
                CourseCard(
                    course = course,
                    onButtonClick = {
                        if (course.progress == 0) {
                            // Nuevo curso - navegar a la primera lección
                            navController.navigate("course_lesson/${course.title}/1")
                        } else {
                            // Curso en progreso - continuar donde quedó
                            navController.navigate("course_resume/${course.title}")
                        }
                    },
                    onDetailsClick = { showCourseDetailsDialog = course }
                )
            }

            // Sección de Oportunidades
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF8FAFC))
                        .padding(vertical = 24.dp, horizontal = 16.dp)
                ) {
                    Text(
                        "WIN WITH YOUR KNOWLEDGE",
                        style = MaterialTheme.typography.labelLarge.copy(
                            color = Color(0xFF38A169),
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Take advantage of these exclusive opportunities for DebtGo consultants.",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = Color(0xFF718096)
                        )
                    )
                }
            }

            // Oportunidades
            items(opportunities) { opportunity ->
                OpportunityCard(
                    opportunity = opportunity,
                    onClick = { showOpportunityDetailsDialog = opportunity }
                )
            }

            // Botón "¡BECOME A CERTIFIED CONSULTANT!"
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 35.dp, vertical = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = { showConsultantConfirmationDialog = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF4A90E2),
                            contentColor = Color.White
                        ),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 2.dp,
                            pressedElevation = 25.dp
                        )
                    ) {
                        Text(
                            "¡BECOME A CERTIFIED CONSULTANT!",
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.5.sp
                            )
                        )
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(8.dp)) }
        }
    }
}

@Composable
fun CourseCard(
    course: Course,
    onButtonClick: () -> Unit,
    onDetailsClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val expandRotation by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        animationSpec = tween(durationMillis = 300, easing = LinearOutSlowInEasing)
    )

    ElevatedCard(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .animateContentSize(animationSpec = tween(durationMillis = 300, easing = LinearOutSlowInEasing))
            .clickable { expanded = !expanded },
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (course.isCertified) {
                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(0xFFEBF5FF))
                                    .padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Verified,
                                    contentDescription = "Certificate",
                                    tint = Color(0xFF3182CE),
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    "CERTIFICATE",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = Color(0xFF3182CE),
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        course.title,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF2D3748)
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    LinearProgressIndicator(
                        progress = course.progress / 100f,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp)),
                        color = Color(0xFF4A90E2),
                        trackColor = Color(0xFFE2E8F0)
                    )
                    Text(
                        "${course.progress}% filled",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = Color(0xFF718096)
                        )
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    FilledTonalButton(
                        onClick = onButtonClick,
                        colors = ButtonDefaults.filledTonalButtonColors(
                            containerColor = if (course.progress == 0) Color(0xFF4A90E2) else Color.Transparent,
                            contentColor = if (course.progress == 0) Color.White else Color(0xFF4A90E2)
                        ),
                        border = if (course.progress > 0) {
                            ButtonDefaults.outlinedButtonBorder
                        } else null,
                        modifier = Modifier.width(110.dp)
                    ) {
                        Text(if (course.progress == 0) "Start" else "Continue", maxLines = 1)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(
                            imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                            contentDescription = if (expanded) "Show less" else "Show more",
                            tint = Color(0xFF718096)
                        )
                    }
                }
            }

            if (expanded) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    course.description,
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color(0xFF4A5568))
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = onDetailsClick,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("View Course Details")
                }
            }
        }
    }
}

@Composable
fun OpportunityCard(opportunity: Opportunity, onClick: () -> Unit) {
    ElevatedCard(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFF0FFF4)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = opportunity.icon,
                    contentDescription = opportunity.title,
                    tint = Color(0xFF38A169),
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    opportunity.title,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF2D3748)
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    opportunity.earnings,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color(0xFF38A169),
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            Icon(
                imageVector = Icons.Outlined.ChevronRight,
                contentDescription = "Show details",
                tint = Color(0xFFA0AEC0)
            )
        }
    }
}