package com.example.debtgo.data.model

data class ChatMessage(
    val id: String,
    val senderId: String,
    val content: String,
    val timestamp: String,
    val isMe: Boolean = false,
    val isSystem: Boolean = false // Para las preguntas generadas por el sistema
)
