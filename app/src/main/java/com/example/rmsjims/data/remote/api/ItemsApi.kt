package com.example.rmsjims.data.remote.api

import android.util.Log
import com.example.rmsjims.data.schema.Items
import com.example.rmsjims.data.remote.apiservice.ItemsApiService
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import java.net.UnknownHostException
import java.io.IOException

class ItemsApi(
    private val supabaseClient: SupabaseClient
) : ItemsApiService {

    private val table = supabaseClient.postgrest["items"]
    
    override suspend fun getItems(): List<Items> {
        Log.d("ItemsApi", "Making Supabase query to 'items' table...")
        Log.d("ItemsApi", "Supabase client URL: ${supabaseClient.supabaseUrl}")
        
        return try {
            val result = table.select().decodeList<Items>()
            Log.d("ItemsApi", "Supabase query successful, returned ${result.size} items")
            if (result.isEmpty()) {
                Log.w("ItemsApi", "WARNING: Query returned empty list. This might indicate:")
                Log.w("ItemsApi", "  1. The 'items' table is empty")
                Log.w("ItemsApi", "  2. Row Level Security (RLS) policies are blocking access")
                Log.w("ItemsApi", "  3. The query filter is too restrictive")
            } else {
                Log.d("ItemsApi", "First 3 items: ${result.take(3).map { it.name }}")
            }
            result
        } catch (e: Exception) {
            // Enhanced error logging
            Log.e("ItemsApi", "=".repeat(60))
            Log.e("ItemsApi", "SUPABASE QUERY FAILED - 'items' table")
            Log.e("ItemsApi", "=".repeat(60))
            Log.e("ItemsApi", "Exception type: ${e.javaClass.name}")
            Log.e("ItemsApi", "Exception message: ${e.message}")
            
            // Get full exception string for pattern matching
            val exceptionString = e.toString().lowercase()
            val causeString = e.cause?.toString()?.lowercase() ?: ""
            val fullExceptionString = "$exceptionString $causeString"
            
            // Check for specific error patterns
            when {
                fullExceptionString.contains("unknownhost") || fullExceptionString.contains("name or service not known") -> {
                    Log.e("ItemsApi", "ERROR TYPE: Network/DNS Error")
                    Log.e("ItemsApi", "Possible causes:")
                    Log.e("ItemsApi", "  - No internet connection")
                    Log.e("ItemsApi", "  - Invalid Supabase URL")
                    Log.e("ItemsApi", "  - DNS resolution failure")
                    Log.e("ItemsApi", "SOLUTION: Check internet connection and verify SUPABASE_URL in local.properties")
                }
                fullExceptionString.contains("timeout") || fullExceptionString.contains("timed out") -> {
                    Log.e("ItemsApi", "ERROR TYPE: Connection Timeout")
                    Log.e("ItemsApi", "Possible causes:")
                    Log.e("ItemsApi", "  - Slow or unstable network connection")
                    Log.e("ItemsApi", "  - Supabase server is down")
                    Log.e("ItemsApi", "  - Firewall blocking the connection")
                    Log.e("ItemsApi", "SOLUTION: Check network connection and try again")
                }
                fullExceptionString.contains("401") || fullExceptionString.contains("unauthorized") -> {
                    Log.e("ItemsApi", "ERROR TYPE: Unauthorized (401)")
                    Log.e("ItemsApi", "Possible causes:")
                    Log.e("ItemsApi", "  - Invalid or expired Supabase anon key")
                    Log.e("ItemsApi", "  - Row Level Security (RLS) policies blocking access")
                    Log.e("ItemsApi", "SOLUTION: Verify SUPABASE_KEY in local.properties and check RLS policies in Supabase dashboard")
                }
                fullExceptionString.contains("403") || fullExceptionString.contains("forbidden") -> {
                    Log.e("ItemsApi", "ERROR TYPE: Forbidden (403)")
                    Log.e("ItemsApi", "Possible causes:")
                    Log.e("ItemsApi", "  - Row Level Security (RLS) policies blocking access")
                    Log.e("ItemsApi", "  - Insufficient permissions")
                    Log.e("ItemsApi", "SOLUTION: Create public read policy for 'items' table in Supabase dashboard")
                }
                fullExceptionString.contains("404") || fullExceptionString.contains("not found") -> {
                    Log.e("ItemsApi", "ERROR TYPE: Not Found (404)")
                    Log.e("ItemsApi", "Possible causes:")
                    Log.e("ItemsApi", "  - The 'items' table does not exist")
                    Log.e("ItemsApi", "  - Invalid Supabase URL or endpoint")
                    Log.e("ItemsApi", "SOLUTION: Create 'items' table in Supabase dashboard or verify table name")
                }
                e is IOException -> {
                    Log.e("ItemsApi", "ERROR TYPE: IO/Network Error")
                    Log.e("ItemsApi", "Possible causes:")
                    Log.e("ItemsApi", "  - Network connectivity issues")
                    Log.e("ItemsApi", "  - Connection reset by server")
                    Log.e("ItemsApi", "SOLUTION: Check internet connection")
                }
                else -> {
                    Log.e("ItemsApi", "ERROR TYPE: Unknown/Other")
                    Log.e("ItemsApi", "Cause: ${e.cause?.javaClass?.name ?: "None"}")
                    Log.e("ItemsApi", "Cause message: ${e.cause?.message ?: "None"}")
                }
            }
            
            // Try to extract HTTP status code if available
            val statusCodeRegex = Regex("""\b(40[0-9]|50[0-9])\b""")
            val statusMatch = statusCodeRegex.find(exceptionString + causeString)
            statusMatch?.value?.let { code ->
                Log.e("ItemsApi", "Detected HTTP status code: $code")
            }
            
            // Log cause chain if available
            var cause: Throwable? = e.cause
            var depth = 0
            while (cause != null && depth < 3) {
                Log.e("ItemsApi", "Caused by (depth $depth): ${cause.javaClass.name} - ${cause.message}")
                cause = cause.cause
                depth++
            }
            
            Log.e("ItemsApi", "Full stack trace:")
            e.printStackTrace()
            Log.e("ItemsApi", "=".repeat(60))
            
            throw e
        }
    }
    
    override suspend fun getItemById(id: Int): Items? {
        Log.d("ItemsApi", "Fetching item with id: $id")
        return try {
            val result = table.select {
                filter {
                    eq("id", id)
                }
            }.decodeList<Items>().firstOrNull()
            Log.d("ItemsApi", if (result != null) "Item found" else "Item not found")
            result
        } catch (e: Exception) {
            Log.e("ItemsApi", "Error fetching item by id: ${e.message}", e)
            throw e
        }
    }
}