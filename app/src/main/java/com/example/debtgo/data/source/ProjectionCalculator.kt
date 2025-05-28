package com.example.debtgo.data.source

import java.time.LocalDate
import java.time.format.DateTimeFormatter

object ProjectionCalculator {
    fun calculatePayoffDate(
        totalDebt: Double,
        monthlyPayment: Double,
        interestRate: Double // Ejemplo: tasa de interés promedio
    ): String {

        if (monthlyPayment <= 0) return "Indefinido"
        val monthsToPay = (totalDebt / monthlyPayment).toInt() // Simplificación extrema
        val payoffDate = LocalDate.now().plusMonths(monthsToPay.toLong())
        val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
        return payoffDate.format(formatter)
    }

    fun calculateRemainingBalance(initialDebt: Double, paidAmount: Double): Double {
        return initialDebt - paidAmount
    }
}