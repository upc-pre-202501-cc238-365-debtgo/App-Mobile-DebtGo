package com.example.debtgo.data.model

data class Contact(
    val id: String,
    val name: String,
    val lastMessage: String,
    val timestamp: String,
    val avatar: String? = null
)