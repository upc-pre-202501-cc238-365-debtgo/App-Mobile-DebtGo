package com.example.debtgo.data.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

enum class NotificationType {
    REMINDER, ACHIEVEMENT, OFFER, WARNING,
    PAYMENT, CONFIRMATION, ACCOUNT;

    fun getColor(): Color {
        return when (this) { // Compara 'this' (la instancia del enum)
            REMINDER -> Color(0xFF3182CE)
            ACHIEVEMENT -> Color(0xFF38A169)
            OFFER -> Color(0xFFDD6B20)
            WARNING -> Color(0xFFE53E3E)
            PAYMENT -> Color.Green // Define un color para PAYMENT
            CONFIRMATION -> Color.Blue // Define un color para CONFIRMATION
            ACCOUNT -> Color.Gray // Define un color para ACCOUNT
        }
    }

    fun getBackground(): Color {
        return when (this) { // Compara 'this' (la instancia del enum)
            REMINDER -> Color(0xFFEBF8FF)
            ACHIEVEMENT -> Color(0xFFF0FFF4)
            OFFER -> Color(0xFFFFFAF0)
            WARNING -> Color(0xFFFFF5F5)
            PAYMENT -> Color(0xFFE6FFE6) // Define un color de fondo para PAYMENT
            CONFIRMATION -> Color(0xFFE6F0FF) // Define un color de fondo para CONFIRMATION
            ACCOUNT -> Color(0xFFF0F0F0) // Define un color de fondo para ACCOUNT
        }
    }
}

data class Notification(
    val id: String,
    val icon: ImageVector,
    val title: String,
    val message: String,
    val time: String,
    val type: NotificationType,
    val read: Boolean = false
)