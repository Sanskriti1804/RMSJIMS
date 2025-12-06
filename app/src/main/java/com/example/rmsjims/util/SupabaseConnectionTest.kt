package com.example.rmsjims.util

import android.util.Log
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest

/**
 * Minimal, opt-in helper for manually testing the Supabase connection.
 *
 * This does NOT change any existing app logic or UI.
 * You can call [testSupabaseConnection] from any coroutine to verify
 * that your Supabase URL and key are valid and reachable.
 */
object SupabaseConnectionTest {

    private const val TAG = "SupabaseConnectionTest"

    /**
     * Runs a very small PostgREST query against the given [tableName].
     *
     * - On success: logs a detailed success message to Logcat with tag [TAG].
     * - On failure: logs the exception so you can inspect the root cause.
     *
     * This function is `suspend` on purpose so you can call it from any
     * existing coroutine scope without introducing new threading logic.
     */
    suspend fun testSupabaseConnection(
        client: SupabaseClient,
        tableName: String
    ): Boolean {
        return try {
            Log.d(TAG, "Starting Supabase connection test on table: $tableName")

            // Use a minimal PostgREST select with a filter builder to limit to 1 row.
            // This keeps the call lightweight but still verifies connectivity & auth.
            val result = client.postgrest[tableName].select {
                limit(1)
            }

            Log.d(
                TAG,
                "Supabase connection test SUCCESS. " +
                        "Table: $tableName, raw result: $result"
            )
            true
        } catch (e: Exception) {
            Log.e(TAG, "Supabase connection test FAILED for table: $tableName", e)
            false
        }
    }
}


