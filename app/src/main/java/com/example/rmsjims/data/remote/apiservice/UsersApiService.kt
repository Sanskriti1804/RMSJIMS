package com.example.rmsjims.data.remote.apiservice

import com.example.rmsjims.data.schema.Users

interface UsersApiService {
    suspend fun getUserByEmail(email: String): Users?
    suspend fun getAllUsers(): List<Users>
    suspend fun addUser(user: Users): Users
}

