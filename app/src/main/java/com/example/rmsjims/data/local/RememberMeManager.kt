package com.example.rmsjims.data.local

import android.content.Context
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class RememberMeManager(context: Context) {

    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val sharedPreferences = EncryptedSharedPreferences.create(
        context,
        PREFS_NAME,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun saveCredentials(username: String, password: String, rememberMe: Boolean) {
        sharedPreferences.edit(commit = true) {
            putString(KEY_USERNAME, username)
            putString(KEY_PASSWORD, password)
            putBoolean(KEY_REMEMBER_ME, rememberMe)
        }
    }

    fun clearCredentials() {
        sharedPreferences.edit(commit = true) {
            remove(KEY_USERNAME)
            remove(KEY_PASSWORD)
            putBoolean(KEY_REMEMBER_ME, false)
        }
    }

    fun setRememberMe(enabled: Boolean) {
        sharedPreferences.edit(commit = true) {
            putBoolean(KEY_REMEMBER_ME, enabled)
        }
    }

    fun isRememberMeEnabled(): Boolean = sharedPreferences.getBoolean(KEY_REMEMBER_ME, false)

    fun getSavedCredentials(): RememberMeData? {
        val rememberMe = isRememberMeEnabled()
        if (!rememberMe) return null

        val username = sharedPreferences.getString(KEY_USERNAME, null)
        val password = sharedPreferences.getString(KEY_PASSWORD, null)

        return if (!username.isNullOrBlank() && !password.isNullOrBlank()) {
            RememberMeData(username, password, rememberMe)
        } else {
            null
        }
    }

    data class RememberMeData(
        val username: String,
        val password: String,
        val rememberMe: Boolean
    )

    companion object {
        private const val PREFS_NAME = "remember_me_prefs"
        private const val KEY_USERNAME = "key_username"
        private const val KEY_PASSWORD = "key_password"
        private const val KEY_REMEMBER_ME = "key_remember_me"
    }
}

