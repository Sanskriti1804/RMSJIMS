package com.example.rmsjims.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rmsjims.data.model.UserRole
import com.example.rmsjims.data.remote.SessionManager
import kotlinx.coroutines.launch

class UserSessionViewModel(
    private val sessionManager: SessionManager
) : ViewModel() {
    var userRole by mutableStateOf(UserRole.USER)
        private set

    init {
        viewModelScope.launch {
            userRole = sessionManager.getUserRole() // now using injected session manager
        }
    }
}

