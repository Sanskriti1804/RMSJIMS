package com.example.rmsjims.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rmsjims.repository.AuthRepository
import com.example.rmsjims.repository.UserSession
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class AuthUiState(
    val isLoading: Boolean = false,
    val isAuthenticated: Boolean = false,
    val errorMessage: String? = null,
    val email: String = "",
    val currentUserId: String? = null
)

class AuthViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val tag = "AuthViewModel"

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState

    // -------- Email / Password --------

    fun signInWithEmail(email: String, password: String) {
        _uiState.value = _uiState.value.copy(
            isLoading = true,
            errorMessage = null,
            email = email
        )

        viewModelScope.launch {
            val result = authRepository.signInWithEmail(email, password)
            handleAuthResult("signInWithEmail", result)
        }
    }

    fun signUpWithEmail(email: String, password: String) {
        _uiState.value = _uiState.value.copy(
            isLoading = true,
            errorMessage = null,
            email = email
        )

        viewModelScope.launch {
            val result = authRepository.signUpWithEmail(email, password)
            handleAuthResult("signUpWithEmail", result)
        }
    }

    // -------- OAuth: Google / GitHub --------

    fun signInWithGoogle() {
        _uiState.value = _uiState.value.copy(
            isLoading = true,
            errorMessage = null
        )

        viewModelScope.launch {
            val result = authRepository.signInWithGoogle()
            result
                .onSuccess {
                    Log.d(tag, "signInWithGoogle: OAuth flow launched")
                    _uiState.value = _uiState.value.copy(isLoading = false)
                }
                .onFailure { e ->
                    Log.e(tag, "signInWithGoogle: FAILED", e)
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Google sign-in failed"
                    )
                }
        }
    }

    fun signInWithGitHub() {
        _uiState.value = _uiState.value.copy(
            isLoading = true,
            errorMessage = null
        )

        viewModelScope.launch {
            val result = authRepository.signInWithGitHub()
            result
                .onSuccess {
                    Log.d(tag, "signInWithGitHub: OAuth flow launched")
                    _uiState.value = _uiState.value.copy(isLoading = false)
                }
                .onFailure { e ->
                    Log.e(tag, "signInWithGitHub: FAILED", e)
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "GitHub sign-in failed"
                    )
                }
        }
    }

    fun signOut() {
        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

        viewModelScope.launch {
            val result = authRepository.signOut()
            result
                .onSuccess {
                    Log.d(tag, "signOut: SUCCESS")
                    _uiState.value = AuthUiState()
                }
                .onFailure { e ->
                    Log.e(tag, "signOut: FAILED", e)
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Sign out failed"
                    )
                }
        }
    }

    private fun handleAuthResult(
        source: String,
        result: Result<UserSession?>
    ) {
        result
            .onSuccess { session ->
                val userId = session?.userId
                Log.d(tag, "$source: SUCCESS, userId=$userId")
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isAuthenticated = userId != null,
                    currentUserId = userId,
                    errorMessage = null
                )
            }
            .onFailure { e ->
                Log.e(tag, "$source: FAILED", e)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isAuthenticated = false,
                    errorMessage = e.message ?: "Authentication failed"
                )
            }
    }
}


