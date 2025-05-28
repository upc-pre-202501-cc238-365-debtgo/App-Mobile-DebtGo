package com.example.debtgo.viewmodel.analysis

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.debtgo.data.model.User
import androidx.compose.runtime.State

class UserViewModel : ViewModel() {
    private val _user = mutableStateOf<User?>(null)
    val user: State<User?> get() = _user

    fun deleteAccount() {
        _user.value = null
    }

}