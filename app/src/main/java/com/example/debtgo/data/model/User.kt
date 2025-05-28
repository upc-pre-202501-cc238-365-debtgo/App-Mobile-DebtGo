package com.example.debtgo.data.model

enum class PlanType {
    BASIC, PREMIUM;

    override fun toString(): String {
        return when (this) {
            BASIC -> "Basic"
            PREMIUM -> "Premium"
        }
    }
}

data class User(
    val name: String,
    val email: String,
    val plan: PlanType
)