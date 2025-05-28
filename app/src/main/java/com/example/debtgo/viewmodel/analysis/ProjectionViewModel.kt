package com.example.debtgo.viewmodel.analysis

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.debtgo.data.model.ProjectionData
import com.example.debtgo.data.repository.AnalysisRepositoryImpl

class ProjectionViewModel : ViewModel() {
    private val analysisRepository = AnalysisRepositoryImpl()

    private val _projectionData = mutableStateOf<ProjectionData?>(null)
    val projectionData: State<ProjectionData?> = _projectionData

    fun calculateProjection(totalDebt: Double, monthlyPayment: Double, interestRate: Double) {
        _projectionData.value = analysisRepository.getPayoffProjection(totalDebt, monthlyPayment, interestRate)
    }
}