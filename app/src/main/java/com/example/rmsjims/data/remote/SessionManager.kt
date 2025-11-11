package com.example.rmsjims.data.remote

import android.content.Context
import com.example.rmsjims.data.model.UserRole

class SessionManager(private val context: Context) {

    private val prefs by lazy {
        context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    }

    fun getUserRole(): UserRole {
        val storedRole = prefs.getString(KEY_USER_ROLE, UserRole.UNASSIGNED.name)
        return runCatching { UserRole.valueOf(storedRole ?: UserRole.UNASSIGNED.name) }
            .getOrDefault(UserRole.UNASSIGNED)
    }

    fun setUserRole(role: UserRole) {
        prefs.edit().putString(KEY_USER_ROLE, role.name).apply()
    }

    fun clearRole() {
        prefs.edit().remove(KEY_USER_ROLE).apply()
    }

    private companion object {
        const val KEY_USER_ROLE = "user_role"
    }
}
