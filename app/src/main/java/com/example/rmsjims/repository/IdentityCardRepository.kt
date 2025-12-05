package com.example.rmsjims.repository

import android.content.Context
import android.net.Uri
import com.example.rmsjims.util.config
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.util.UUID

class IdentityCardRepository {

    private val httpClient by lazy {
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }
    }

    /**
     * Uploads an identity card image to Supabase Storage bucket "Idcards".
     *
     * @param context Android context used to read the file from [uri].
     * @param userKey A logical key to group objects per user, e.g. user id or email.
     * @param uri The local Uri of the selected image.
     * @return Result containing the stored object path on success.
     */
    suspend fun uploadIdentityCard(
        context: Context,
        userKey: String,
        uri: Uri
    ): Result<String> = withContext(Dispatchers.IO) {
        runCatching {
            val inputStream = context.contentResolver.openInputStream(uri)
                ?: error("Unable to open identity card image")

            val bytes = inputStream.use { it.readBytes() }

            val supabaseUrl = config.SUPABASE_URL.trimEnd('/')
            val supabaseKey = config.SUPABASE_KEY
            val bucket = "Idcards"

            // Give every ID card a unique path in the bucket
            val objectPath = "users/$userKey/${UUID.randomUUID()}.jpg"

            val response = httpClient.post("$supabaseUrl/storage/v1/object/$bucket/$objectPath") {
                // Supabase expects the anon key both as apikey header and Bearer token
                header("apikey", supabaseKey)
                header(HttpHeaders.Authorization, "Bearer $supabaseKey")
                header(HttpHeaders.ContentType, ContentType.Image.JPEG.toString())
                header("x-upsert", "true")
                setBody(bytes)
            }

            val statusCode = response.status.value
            val responseBody = try {
                response.bodyAsText()
            } catch (e: Exception) {
                "Unable to read response body: ${e.message}"
            }

            if (!response.status.isSuccess()) {
                val errorMsg = "Supabase upload failed: HTTP $statusCode - $responseBody"
                android.util.Log.e("IdentityCardRepository", errorMsg)
                android.util.Log.e("IdentityCardRepository", "Upload URL: $supabaseUrl/storage/v1/object/$bucket/$objectPath")
                error(errorMsg)
            }

            objectPath
        }
    }
}


