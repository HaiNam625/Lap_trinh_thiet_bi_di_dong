package com.example.baitrenlop.data.repository

import com.example.baitrenlop.data.model.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class AuthResult {
    data class Success(val user: UserProfile, val message: String) : AuthResult()
    data class Error(val message: String) : AuthResult()
}

class AuthRepository {

    private val _currentUser = MutableStateFlow<UserProfile?>(
        UserProfile(
            uid = "user_default",
            name = "Minh Anh",
            email = "minhanh@example.com",
            isGoogleLinked = true,
            isAnonymous = false
        )
    )
    val currentUser: StateFlow<UserProfile?> = _currentUser.asStateFlow()

    fun signInWithEmail(email: String, password: String): AuthResult {
        val trimmedEmail = email.trim()
        val trimmedPassword = password.trim()

        if (trimmedEmail.isEmpty()) {
            return AuthResult.Error("Vui lòng nhập Email!")
        }
        if (!trimmedEmail.contains("@") || !trimmedEmail.contains(".")) {
            return AuthResult.Error("Email không hợp lệ! Vui lòng kiểm tra lại.")
        }
        if (trimmedPassword.isEmpty()) {
            return AuthResult.Error("Vui lòng nhập Mật khẩu!")
        }
        if (trimmedPassword.length < 6) {
            return AuthResult.Error("Mật khẩu phải có ít nhất 6 ký tự!")
        }

        // Test login logic
        val nameFromEmail = trimmedEmail.substringBefore("@").replace(".", " ").capitalizeWords()
        val user = UserProfile(
            uid = "user_${trimmedEmail.hashCode()}",
            name = nameFromEmail.ifEmpty { "Minh Anh" },
            email = trimmedEmail,
            isGoogleLinked = false,
            isAnonymous = false
        )
        _currentUser.value = user
        return AuthResult.Success(user, "Đăng nhập thành công! Chào mừng $nameFromEmail.")
    }

    fun signInWithGoogle(): AuthResult {
        val user = UserProfile(
            uid = "google_user_101",
            name = "Minh Anh",
            email = "minhanh@example.com",
            isGoogleLinked = true,
            isAnonymous = false
        )
        _currentUser.value = user
        return AuthResult.Success(user, "Đăng nhập Google thành công! Chào mừng Minh Anh.")
    }

    fun signInAsGuest(): AuthResult {
        val user = UserProfile(
            uid = "guest_${System.currentTimeMillis()}",
            name = "Khách Hàng",
            email = "khach@uthtodo.app",
            isGoogleLinked = false,
            isAnonymous = true
        )
        _currentUser.value = user
        return AuthResult.Success(user, "Đăng nhập trải nghiệm thành công!")
    }

    fun signOut(): AuthResult {
        _currentUser.value = null
        return AuthResult.Success(
            UserProfile(uid = "", name = "", email = ""),
            "Đã đăng xuất thành công!"
        )
    }

    private fun String.capitalizeWords(): String {
        return split(" ").joinToString(" ") { word ->
            word.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
        }
    }
}
