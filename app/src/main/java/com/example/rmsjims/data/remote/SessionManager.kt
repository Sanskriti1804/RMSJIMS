package com.example.rmsjims.data.remote

import android.content.Context
import com.example.rmsjims.data.model.UserRole

class SessionManager(private val context: Context) {
    fun getUserRole(): UserRole {
        // FOR TESTING ONLY — hardcoded override
        return UserRole.USER  // or UserRole.USER
    }


//    fun getUserRole(): UserRole {
//
//        // FOR TESTING ONLY — manually switch here:
//        val testRole = UserRole.LAB_INCHARGE  // or USER
//
//        // example using SharedPreferences
//        val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
//        val role = prefs.getString("user_role", UserRole.USER.name)
//        return UserRole.valueOf(role ?: UserRole.USER.name)
//    }
}
