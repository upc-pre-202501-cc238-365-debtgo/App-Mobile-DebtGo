package com.example.debtgo.data.model

import androidx.compose.ui.graphics.vector.ImageVector

data class Course(
    val title: String,
    val progress: Int,
    val icon: ImageVector,
    val isCertified: Boolean,
    val description: String = ""
)
