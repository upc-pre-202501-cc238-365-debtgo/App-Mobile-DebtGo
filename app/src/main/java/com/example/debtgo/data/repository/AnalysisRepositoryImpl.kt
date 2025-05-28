package com.example.debtgo.data.repository

import com.example.debtgo.data.model.ProjectionData
import com.example.debtgo.data.source.ProjectionCalculator

class AnalysisRepositoryImpl : AnalysisRepository {
    override fun getPayoffProjection(totalDebt: Double, monthlyPayment: Double, interestRate: Double): ProjectionData {
        val estimatedDate = ProjectionCalculator.calculatePayoffDate(totalDebt, monthlyPayment, interestRate)
        val remaining = ProjectionCalculator.calculateRemainingBalance(totalDebt, totalDebt - (totalDebt / 3)) // Ejemplo de remaining
        return ProjectionData(estimatedDate, remaining, monthlyPayment)
    }
}