package com.example.debtgo.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.debtgo.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseLessonScreen(
    title: String,
    lessonNumber: Int,
    navController: NavController,
    onCourseCompleted: () -> Unit
) {
    val totalLessons = 6
    val scrollState = rememberScrollState()

    var showCompletionDialog by remember { mutableStateOf(false) }

    if (showCompletionDialog) {
        AlertDialog(
            onDismissRequest = { showCompletionDialog = false },
            title = { Text("Course Completed!") },
            text = { Text("Congratulations! You've successfully completed this course.") },
            confirmButton = {
                Button(
                    onClick = {
                        showCompletionDialog = false
                        onCourseCompleted()
                        navController.navigate(Screen.Education.route) {
                            popUpTo(Screen.CourseLesson.route.replace("/{title}/{lessonNumber}", "")) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }
                ) {
                    Text("OK")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(title, style = MaterialTheme.typography.titleSmall)
                        Text(
                            "Lesson $lessonNumber of $totalLessons",
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        // Si estamos en la primera lección, volvemos a la pantalla de detalles del curso
                        if (lessonNumber == 1) {
                            navController.navigate("course_detail/${title}") {
                                popUpTo(Screen.CourseLesson.route.replace("/{title}/{lessonNumber}", "")) {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
                        } else {
                            // Si no es la primera lección, navegamos a la lección anterior
                            navController.navigate(Screen.CourseLesson.createRoute(title, lessonNumber - 1))
                        }
                    }) {
                        Icon(Icons.Outlined.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /* Mark as completed */ }) {
                        Icon(
                            imageVector = Icons.Outlined.CheckCircle,
                            contentDescription = "Mark as completed"
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = {
                            navController.navigate(Screen.CourseLesson.createRoute(title, lessonNumber - 1))
                        },
                        enabled = lessonNumber > 1
                    ) {
                        Text("Previous")
                    }

                    if (lessonNumber < totalLessons) {
                        Button(
                            onClick = {
                                navController.navigate(Screen.CourseLesson.createRoute(title, lessonNumber + 1))
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF4A90E2),
                                contentColor = Color.White
                            )
                        ) {
                            Text("Next Lesson")
                        }
                    } else {
                        Button(
                            onClick = { showCompletionDialog = true },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF38A169),
                                contentColor = Color.White
                            )
                        ) {
                            Text("Finish Course")
                        }
                    }
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
            // Contenido de la lección (simulado)
            Text(
                "Lesson $lessonNumber: ${getLessonTitle(lessonNumber)}",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Video placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Text("Video Lesson $lessonNumber", color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Contenido de texto
            Text(
                "This is the detailed content for lesson $lessonNumber. Here you would include all the " +
                        "educational material, explanations, examples, and exercises for this specific lesson.",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Sección de notas
            OutlinedTextField(
                value = "",
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Take notes...") },
                maxLines = 4
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Material adicional
            Text(
                "Additional Resources",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )

            Spacer(modifier = Modifier.height(8.dp))

            val resources = listOf(
                "Lesson ${lessonNumber} Slides (PDF)",
                "Supplementary Reading",
                "Practice Exercises"
            )

            resources.forEach { resource ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Description,
                            contentDescription = "Resource"
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(resource)
                    }
                }
            }
        }
    }
}

private fun getLessonTitle(lessonNumber: Int): String {
    return when (lessonNumber) {
        1 -> "Introduction"
        2 -> "Fundamental Concepts"
        3 -> "Practical Applications"
        4 -> "Advanced Techniques"
        5 -> "Case Studies"
        6 -> "Final Assessment"
        else -> "Lesson $lessonNumber"
    }
}