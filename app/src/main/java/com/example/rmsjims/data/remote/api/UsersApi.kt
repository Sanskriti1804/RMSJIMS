package com.example.rmsjims.data.remote.api

import android.util.Log
import com.example.rmsjims.data.schema.Users
import com.example.rmsjims.data.remote.apiservice.UsersApiService
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UsersApi(
    private val supabaseClient: SupabaseClient
) : UsersApiService {

    private val table = supabaseClient.postgrest["users"]
    
    override suspend fun getUserByEmail(email: String): Users? = withContext(Dispatchers.IO) {
        Log.d("UsersApi", "Querying user by email: $email")
        return@withContext try {
            val result = table.select {
                filter {
                    eq("email", email.lowercase().trim())
                }
            }.decodeSingleOrNull<Users>()
            
            if (result != null) {
                Log.d("UsersApi", "User found: ${result.email}, role: ${result.role}")
            } else {
                Log.d("UsersApi", "No user found with email: $email")
            }
            result
        } catch (e: Exception) {
            Log.e("UsersApi", "Error querying user by email", e)
            null
        }
    }

    override suspend fun getAllUsers(): List<Users> = withContext(Dispatchers.IO) {
        Log.d("UsersApi", "Fetching all users")
        return@withContext try {
            val result = table.select().decodeList<Users>()
            Log.d("UsersApi", "Fetched ${result.size} users")
            result
        } catch (e: Exception) {
            Log.e("UsersApi", "Error fetching all users", e)
            emptyList()
        }
    }

    override suspend fun addUser(user: Users): Users = withContext(Dispatchers.IO) {
        Log.d("UsersApi", "Adding new user: ${user.email}, role: ${user.role}")
        return@withContext try {
            // Normalize email to lowercase
            val normalizedUser = user.copy(email = user.email.lowercase().trim())
            
            // Insert user (id should be null for auto-generation)
            // Supabase PostgREST returns an array by default, so we use decodeList and take first
            val insertResult = table.insert(normalizedUser)
            
            // Try decodeList first (PostgREST typically returns arrays)
            val result = try {
                val list = insertResult.decodeList<Users>()
                if (list.isEmpty()) {
                    throw IllegalStateException("Insert returned empty array")
                }
                list.first()
            } catch (e: Exception) {
                // If decodeList fails, try decodeSingle as fallback
                Log.d("UsersApi", "decodeList failed, trying decodeSingle: ${e.message}")
                try {
                    insertResult.decodeSingle<Users>()
                } catch (e2: Exception) {
                    Log.e("UsersApi", "Both decodeList and decodeSingle failed", e2)
                    throw IllegalStateException("Failed to decode insert response: ${e2.message}")
                }
            }
            
            Log.d("UsersApi", "User added successfully: ${result.email}, id: ${result.id}")
            result
        } catch (e: Exception) {
            Log.e("UsersApi", "Error adding user: ${e.message}", e)
            throw e
        }
    }
}

