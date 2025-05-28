package com.example.debtgo.data.model

data class Review(
    val id: String,
    val userName: String,
    val rating: Float, // Ej: 4.5
    val comment: String,
    val date: String // "10 May 2024"
)