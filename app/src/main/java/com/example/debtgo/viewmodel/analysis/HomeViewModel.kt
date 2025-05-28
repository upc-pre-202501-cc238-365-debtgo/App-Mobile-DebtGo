package com.example.debtgo.viewmodel.analysis

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _totalDebt = MutableStateFlow<Double?>(0.0)
    val totalDebt: StateFlow<Double?> = _totalDebt.asStateFlow()

    private val _monthlyPayment = MutableStateFlow<Double?>(0.0)
    val monthlyPayment: StateFlow<Double?> = _monthlyPayment.asStateFlow()

    private val _progress = MutableStateFlow(0.0f)
    val progress: StateFlow<Float> = _progress.asStateFlow()

    private val _interestRate = MutableStateFlow(0.0)
    val interestRate: StateFlow<Double> = _interestRate.asStateFlow()

    private val _estimatedPayoffDate = MutableStateFlow("")
    val estimatedPayoffDate: StateFlow<String> = _estimatedPayoffDate.asStateFlow()

    private val _remainingBalance = MutableStateFlow(0.0)
    val remainingBalance: StateFlow<Double> = _remainingBalance.asStateFlow()

    private val _isBasicPlan = MutableStateFlow(false)
    val isBasicPlan: StateFlow<Boolean> = _isBasicPlan.asStateFlow()

    init {
        loadDebtData()
    }

    private fun loadDebtData() {
        viewModelScope.launch {
            kotlinx.coroutines.delay(500)
            _totalDebt.value = 12500.00
            _monthlyPayment.value = 450.00
            _progress.value = 0.35f
            _interestRate.value = 0.05
            _estimatedPayoffDate.value = "2027-08-13"
            _remainingBalance.value = 8125.00
            _isBasicPlan.value = true
        }
    }
}