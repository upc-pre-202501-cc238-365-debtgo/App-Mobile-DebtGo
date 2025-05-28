package com.example.debtgo.viewmodel.analysis

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.debtgo.data.model.Service

class ServiceViewModel : ViewModel() {
    var title by mutableStateOf("")
    var description by mutableStateOf("")
    var prices = mutableStateListOf<String>()
    var fileName by mutableStateOf("")

    var selectedService by mutableStateOf<Service?>(null)

    fun addPriceOption(price: String) {
        prices.add(price)
    }

    fun selectService(service: Service) {
        selectedService = service
    }

    fun clear() {
        title = ""
        description = ""
        prices.clear()
        fileName = ""
    }

    fun updateService() {
        selectedService?.let {
            it.title = title
            it.description = description
            it.prices = prices
            // guarda en base de datos o lista principal si es necesario
        }
    }

}

