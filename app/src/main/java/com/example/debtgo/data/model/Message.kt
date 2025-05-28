package com.example.debtgo.data.model

data class Message(
    val id: String,
    val sender: String,
    val content: String,
    val isUser: Boolean = false
)