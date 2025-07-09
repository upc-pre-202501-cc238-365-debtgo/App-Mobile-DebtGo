package com.example.debtgo.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.debtgo.data.model.Review

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewsScreen(navController: NavController) {
    val reviews = remember { mutableStateListOf(*getSampleReviews().toTypedArray()) }
    var editingReview by remember { mutableStateOf<Review?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Reviews") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            items(reviews, key = { it.id }) { review ->
                ReviewItem(
                    review = review,
                    onEdit = { editingReview = it },
                    onDelete = { reviews.remove(it) }
                )
            }
        }

        editingReview?.let { review ->
            EditReviewDialog(
                review = review,
                onDismiss = { editingReview = null },
                onSave = { updatedReview ->
                    val index = reviews.indexOfFirst { it.id == updatedReview.id }
                    if (index != -1) {
                        reviews[index] = updatedReview
                    }
                    editingReview = null
                }
            )
        }
    }
}

@Composable
fun ReviewItem(
    review: Review,
    onEdit: (Review) -> Unit,
    onDelete: (Review) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = review.userName, fontWeight = FontWeight.Bold)
                Row {
                    IconButton(onClick = { onEdit(review) }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit")
                    }
                    IconButton(onClick = { onDelete(review) }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete")
                    }
                }
            }
            StarRating(rating = review.rating)
            Text(text = review.comment, modifier = Modifier.padding(top = 8.dp))
            Text(
                text = review.date,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
fun StarRating(rating: Float) {
    Row {
        repeat(5) { index ->
            Icon(
                imageVector = if (index < rating.toInt()) Icons.Filled.Star else Icons.Outlined.Star,
                contentDescription = null,
                tint = if (index < rating.toInt()) Color.Yellow else Color.Gray
            )
        }
    }
}

@Composable
fun EditReviewDialog(
    review: Review,
    onDismiss: () -> Unit,
    onSave: (Review) -> Unit
) {
    var updatedComment by remember { mutableStateOf(review.comment) }
    var updatedRating by remember { mutableStateOf(review.rating) }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onSave(review.copy(comment = updatedComment, rating = updatedRating))
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        title = { Text("Edit Review") },
        text = {
            Column {
                TextField(
                    value = updatedComment,
                    onValueChange = { updatedComment = it },
                    label = { Text("Comment") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = updatedRating.toString(),
                    onValueChange = {
                        val newRating = it.toFloatOrNull()
                        if (newRating != null && newRating in 0f..5f) updatedRating = newRating
                    },
                    label = { Text("Rating (0-5)") }
                )
            }
        }
    )
}

private fun getSampleReviews(): List<Review> = listOf(
    Review("1", "Carlos López", 4.5f, "¡Excellent service!", "10 May 2024"),
    Review("2", "Ana Torres", 5f, "DebtGo saved my business", "8 May 2024"),
    Review("3", "María González", 4f, "Very professional!", "5 May 2024")
)
