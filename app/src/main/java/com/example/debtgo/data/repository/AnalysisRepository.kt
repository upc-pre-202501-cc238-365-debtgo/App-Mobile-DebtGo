package com.example.debtgo.data.repository

import com.example.debtgo.data.model.ProjectionData

interface AnalysisRepository {
    fun getPayoffProjection(totalDebt: Double, monthlyPayment: Double, interestRate: Double): ProjectionData
}