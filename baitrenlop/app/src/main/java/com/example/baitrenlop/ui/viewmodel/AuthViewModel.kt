package com.example.baitrenlop.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.baitrenlop.data.model.UserProfile
import com.example.baitrenlop.data.repository.AuthRepository
import com.example.baitrenlop.data.repository.AuthResult
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class AuthUiMessage {
    data class Success(val message: String) : AuthUiMessage()
    data class Error(val message: String) : AuthUiMessage()
}

class AuthViewModel(
    private val repository: AuthRepository = AuthRepository()
) : ViewModel() {

    val currentUser: StateFlow<UserProfile?> = repository.currentUser

    private val _uiMessage = MutableSharedFlow<AuthUiMessage>()
    val uiMessage: SharedFlow<AuthUiMessage> = _uiMessage.asSharedFlow()

    private val _isLoggedIn = MutableStateFlow(repository.currentUser.value != null)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    fun signInWithEmail(email: String, pass: String, onSuccess: () -> Unit) {
        when (val result = repository.signInWithEmail(email, pass)) {
            is AuthResult.Success -> {
                _isLoggedIn.value = true
                _uiMessage.tryEmit(AuthUiMessage.Success(result.message))
                onSuccess()
            }
            is AuthResult.Error -> {
                _uiMessage.tryEmit(AuthUiMessage.Error(result.message))
            }
        }
    }

    fun signInWithGoogle(onSuccess: () -> Unit) {
        when (val result = repository.signInWithGoogle()) {
            is AuthResult.Success -> {
                _isLoggedIn.value = true
                _uiMessage.tryEmit(AuthUiMessage.Success(result.message))
                onSuccess()
            }
            is AuthResult.Error -> {
                _uiMessage.tryEmit(AuthUiMessage.Error(result.message))
            }
        }
    }

    fun signInAsGuest(onSuccess: () -> Unit) {
        when (val result = repository.signInAsGuest()) {
            is AuthResult.Success -> {
                _isLoggedIn.value = true
                _uiMessage.tryEmit(AuthUiMessage.Success(result.message))
                onSuccess()
            }
            is AuthResult.Error -> {
                _uiMessage.tryEmit(AuthUiMessage.Error(result.message))
            }
        }
    }

    fun signOut(onSignedOut: () -> Unit) {
        val result = repository.signOut()
        _isLoggedIn.value = false
        if (result is AuthResult.Success) {
            _uiMessage.tryEmit(AuthUiMessage.Success(result.message))
        }
        onSignedOut()
    }
}
