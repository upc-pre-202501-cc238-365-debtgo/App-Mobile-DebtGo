import androidx.lifecycle.ViewModel
import com.example.debtgo.ui.screen.PaymentConfiguration
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PaymentViewModel : ViewModel() {
    private val _paymentConfigurations = MutableStateFlow<List<PaymentConfiguration>>(emptyList())
    val paymentConfigurations: StateFlow<List<PaymentConfiguration>> = _paymentConfigurations.asStateFlow()

    fun addPaymentConfiguration(config: com.example.debtgo.data.model.PaymentConfiguration) {
        _paymentConfigurations.value = (_paymentConfigurations.value + config) as List<*> as List<PaymentConfiguration>
    }
}