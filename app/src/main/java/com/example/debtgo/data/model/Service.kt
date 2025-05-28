package com.example.debtgo.data.model

data class Service(
    val id: Int,
    var title: String,
    var description: String,
    var prices: List<String>,
    var isActive: Boolean
)
