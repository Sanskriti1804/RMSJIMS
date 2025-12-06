package com.example.rmsjims.ui.screens.staff

import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.rmsjims.util.config
import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.isSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

/**
 * Uploads an identity card image to Supabase Storage bucket.
 *
 * This uses the existing SUPABASE_URL and SUPABASE_KEY from config and uploads
 * the content at [uri] into the [bucketName] bucket. Each upload gets a unique ID.
 * It returns the path inside the bucket on success, or a failure on error.
 */
suspend fun uploadIdentityCardToSupabase(
    context: Context,
    client: HttpClient,
    uri: Uri,
    bucketName: String
): Result<String> = withContext(Dispatchers.IO) {
    runCatching {
        val tag = "IdentityCardUpload"
        
        Log.d(tag, "Starting identity card upload...")
        
        val inputStream = context.contentResolver.openInputStream(uri)
            ?: error("Unable to open selected file")
        
        val bytes = inputStream.use { it.readBytes() }
        Log.d(tag, "Read ${bytes.size} bytes from image")

        val supabaseUrl = config.SUPABASE_URL.trimEnd('/')
        val supabaseKey = config.SUPABASE_KEY

        Log.d(tag, "Supabase URL: $supabaseUrl")
        Log.d(tag, "Supabase key prefix: ${supabaseKey.take(15)}")

        // Give every ID card a unique path in the bucket
        val uniqueId = UUID.randomUUID()
        val fileName = "${uniqueId}_identity.jpg"
        val objectPath = fileName // Path within the bucket (not including bucket name)

        // Supabase Storage v1 upload endpoint format: storage/v1/object/{bucket}/{path}
        // Note: Path should be URL encoded if it contains special characters
        val encodedPath = objectPath.replace(" ", "%20")
        val uploadUrl = "$supabaseUrl/storage/v1/object/$bucketName/$encodedPath"
        
        Log.d(tag, "Uploading to: $uploadUrl")
        Log.d(tag, "Bucket: $bucketName, Object path: $objectPath")
        Log.d(tag, "File size: ${bytes.size} bytes")

        val response = try {
            client.post(uploadUrl) {
                // Supabase Storage API requires these headers
                header("apikey", supabaseKey)
                header(HttpHeaders.Authorization, "Bearer $supabaseKey")
                // For binary uploads, use the specific image type
                header(HttpHeaders.ContentType, "image/jpeg")
                // x-upsert allows overwriting existing files
                header("x-upsert", "true")
                // Set the body as raw bytes
                setBody(bytes)
            }
        } catch (e: Exception) {
            Log.e(tag, "Exception during HTTP request", e)
            Log.e(tag, "Exception type: ${e.javaClass.simpleName}")
            Log.e(tag, "Exception message: ${e.message}")
            e.printStackTrace()
            throw e
        }

        val statusCode = response.status.value
        val responseBody = try {
            response.bodyAsText()
        } catch (e: Exception) {
            "Unable to read response body: ${e.message}"
        }

        Log.d(tag, "Upload response - Status: $statusCode")
        Log.d(tag, "Response body: $responseBody")

        if (response.status.isSuccess()) {
            Log.d(tag, "Upload successful! Status: ${response.status}, Object path: $objectPath")
            objectPath
        } else {
            // Provide more helpful error messages based on status code
            val errorMsg = when (statusCode) {
                401 -> "Unauthorized: Check your Supabase anon key. Make sure you're using the 'anon public' key, not the service role key."
                403 -> "Forbidden: Storage bucket policies may be blocking uploads. Check your Supabase Storage policies for the '$bucketName' bucket. Even PUBLIC buckets need INSERT policies."
                404 -> "Bucket not found: The bucket '$bucketName' doesn't exist or the name is incorrect. Check your Supabase dashboard."
                413 -> "File too large: The file exceeds Supabase's size limit (default 50MB)."
                else -> "Supabase upload failed: HTTP $statusCode - $responseBody"
            }
            
            Log.e(tag, "=".repeat(60))
            Log.e(tag, "UPLOAD FAILED")
            Log.e(tag, "Status Code: $statusCode")
            Log.e(tag, "Error: $errorMsg")
            Log.e(tag, "Response: $responseBody")
            Log.e(tag, "Upload URL: $uploadUrl")
            Log.e(tag, "Bucket: $bucketName")
            Log.e(tag, "Object path: $objectPath")
            Log.e(tag, "File size: ${bytes.size} bytes")
            Log.e(tag, "=".repeat(60))
            
            error(errorMsg)
        }
    }
}


