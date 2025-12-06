package com.example.rmsjims.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Minimal stub for authentication.
 *
 * Because of breaking changes / missing artifacts in the Supabase Kotlin SDK
 * version currently in use, wiring real Supabase Auth here would require
 * upgrading your networking stack (Supabase + Ktor) across the app.
 *
 * To keep your existing code compiling and your UI/theme untouched, this class
 * logs calls and returns failures that you can see in Logcat.
 */

data class UserSession(
    val userId: String? = null
)

class AuthRepository {

    private val tag = "AuthRepository"

    // -------- Email / Password --------

    suspend fun signInWithEmail(
        email: String,
        password: String
    ): Result<UserSession?> = withContext(Dispatchers.IO) {
        Log.w(tag, "signInWithEmail: Supabase Auth not wired (stub) for $email")
        Result.failure(IllegalStateException("Supabase Auth not configured for this build"))
    }

    suspend fun signUpWithEmail(
        email: String,
        password: String
    ): Result<UserSession?> = withContext(Dispatchers.IO) {
        Log.w(tag, "signUpWithEmail: Supabase Auth not wired (stub) for $email")
        Result.failure(IllegalStateException("Supabase Auth not configured for this build"))
    }

    // -------- OAuth: Google --------

    suspend fun signInWithGoogle(): Result<Unit> = withContext(Dispatchers.IO) {
        Log.w(tag, "signInWithGoogle: Supabase Auth not wired (stub)")
        Result.failure(IllegalStateException("Supabase Auth not configured for this build"))
    }

    // -------- OAuth: GitHub --------

    suspend fun signInWithGitHub(): Result<Unit> = withContext(Dispatchers.IO) {
        Log.w(tag, "signInWithGitHub: Supabase Auth not wired (stub)")
        Result.failure(IllegalStateException("Supabase Auth not configured for this build"))
    }

    // -------- Session helpers --------

    suspend fun getCurrentSessionStatus(): String = withContext(Dispatchers.IO) {
        val status = "UNKNOWN (Supabase Auth not configured for this build)"
        Log.w(tag, "getCurrentSessionStatus: $status")
        status
    }

    suspend fun signOut(): Result<Unit> = withContext(Dispatchers.IO) {
        Log.w(tag, "signOut: Supabase Auth not wired (stub)")
        Result.failure(IllegalStateException("Supabase Auth not configured for this build"))
    }
}
