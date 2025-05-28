package com.example.debtgo.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Verified
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.debtgo.ui.components.CourseInfoItem
import com.example.debtgo.ui.components.LessonItem
import com.example.debtgo.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseDetailScreen(
    title: String,
    isCompleted: Boolean = false,
    navController: NavController,
    description: String
) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            title,
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                        )
                        if (isCompleted) {
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                imageVector = Icons.Outlined.Verified,
                                contentDescription = "Completed",
                                tint = Color(0xFF38A169)
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(Screen.Education.route) {
                            popUpTo(Screen.CourseDetail.route.replace("/{title}", "")) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }) {
                        Icon(Icons.Outlined.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            if (isCompleted) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF0FFF4))
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.CheckCircle,
                            contentDescription = "Completed",
                            tint = Color(0xFF38A169)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            "You´ve completed this course!",
                            style = MaterialTheme.typography.bodyLarge.copy(color = Color(0xFF38A169))
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Tarjeta de información del curso
            ElevatedCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "Course Details",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF4A90E2)
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Información simulada del curso
                    CourseInfoItem("Duration", "8 weeks")
                    CourseInfoItem("Level", "Intermediate")
                    CourseInfoItem("Certification", "Available")
                    CourseInfoItem("Instructor", "Financial Experts Team")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Descripción del curso
            Text(
                "About this course",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                description, // Display the description here
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Lista de lecciones (simulada)
            Text(
                "Course Content",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(8.dp))

            val lessons = listOf(
                "Introduction to the Course",
                "Fundamental Concepts",
                "Practical Applications",
                "Advanced Techniques",
                "Case Studies",
                "Final Assessment"
            )
            lessons.forEachIndexed { index, lesson ->
                LessonItem(
                    number = index + 1,
                    title = lesson,
                    duration = if (index == 0) "15 min" else "${20 + index * 5} min",
                    onClick = {
                        navController.navigate(
                            Screen.CourseLesson.createRoute(
                                title,
                                index + 1
                            )
                        )
                    }
                )
                if (index < lessons.lastIndex) Spacer(modifier = Modifier.height(8.dp))
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Botón para comenzar/continuar
            Button(
                onClick = {
                    navController.navigate(Screen.CourseLesson.createRoute(title, 1))
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isCompleted) Color(0xFF38A169) else Color(0xFF4A90E2),
                    contentColor = Color.White
                )
            ) {
                Text(
                    if (isCompleted) "Review Course" else "Start Course",
                    fontSize = 16.sp
                )
            }
        }
    }
}
