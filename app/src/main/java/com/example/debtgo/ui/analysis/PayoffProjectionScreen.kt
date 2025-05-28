package com.example.debtgo.ui.analysis

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.debtgo.viewmodel.analysis.ProjectionViewModel

@Composable
fun PayoffProjectionScreen(totalDebt: Double, monthlyPayment: Double, interestRate: Double) {
    val projectionViewModel: ProjectionViewModel = viewModel()
    projectionViewModel.calculateProjection(totalDebt, monthlyPayment, interestRate)
    val projectionData by projectionViewModel.projectionData

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Proyección de Pago")
        Spacer(modifier = Modifier.height(16.dp))
        projectionData?.let {
            Text(text = "Fecha Estimada de Pago: ${it.estimatedPayoffDate}")
            Text(text = "Saldo Restante: $${String.format("%.2f", it.remainingBalance)}")
            Text(text = "Pago Mensual Actual: $${String.format("%.2f", it.monthlyPayment)}")
        } ?: run {
            Text(text = "Calculando proyección...")
        }
    }
}