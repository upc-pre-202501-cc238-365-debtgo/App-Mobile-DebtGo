package com.example.debtgo.data.model

import androidx.compose.ui.graphics.vector.ImageVector

data class Opportunity(
    val title: String,
    val earnings: String,
    val icon: ImageVector,
    val description: String
)