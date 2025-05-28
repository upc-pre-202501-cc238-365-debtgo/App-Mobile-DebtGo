package com.example.debtgo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.example.debtgo.ui.navigation.AppNavigation
import com.example.debtgo.ui.theme.DebtGoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DebtGoTheme {
                val navController = rememberNavController()
                AppNavigation(navController = navController)
            }
        }
    }
}