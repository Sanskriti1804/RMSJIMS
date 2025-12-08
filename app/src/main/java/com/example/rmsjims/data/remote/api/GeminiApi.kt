package com.example.rmsjims.data.remote.api

import android.util.Log
import com.example.rmsjims.data.model.AiSuggestion
import com.example.rmsjims.data.remote.apiservice.GeminiApiService
import com.example.rmsjims.util.config
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class GeminiApi : GeminiApiService {
    
    private val httpClient by lazy {
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }
    }
    
    private val apiKey = try {
        config.GEMINI_API_KEY
    } catch (e: Exception) {
        Log.e("GeminiApi", "Failed to get API key: ${e.message}", e)
        throw e
    }
    private val baseUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent"
    
    @Serializable
    data class GeminiRequest(
        val contents: List<Content>
    ) {
        @Serializable
        data class Content(
            val parts: List<Part>
        ) {
            @Serializable
            data class Part(
                val text: String
            )
        }
    }
    
    @Serializable
    data class GeminiResponse(
        val candidates: List<Candidate>?
    ) {
        @Serializable
        data class Candidate(
            val content: Content
        ) {
            @Serializable
            data class Content(
                val parts: List<Part>
            ) {
                @Serializable
                data class Part(
                    val text: String
                )
            }
        }
    }
    
    override suspend fun getEquipmentSuggestions(
        projectDescription: String,
        equipmentList: List<GeminiApiService.EquipmentInfo>
    ): Result<AiSuggestion> = withContext(Dispatchers.IO) {
        try {
            Log.d("GeminiApi", "Generating equipment suggestions for project: $projectDescription")
            
            // Build equipment list text
            val equipmentText = equipmentList.joinToString("\n") { eq ->
                val status = if (eq.isAvailable) "Available" else "Not Available"
                val waitingStatus = if (eq.requestStatus?.contains("waiting", ignoreCase = true) == true) " (Waiting)" else ""
                val buildingInfo = if (eq.buildingName != null) {
                    "${eq.buildingName}${eq.buildingNumber?.let { " ($it)" } ?: ""}"
                } else {
                    "Unknown Building"
                }
                "- ${eq.name} | Building: $buildingInfo | Department: ${eq.departmentName ?: "N/A"} | Location: ${eq.location ?: "N/A"} | Status: $status$waitingStatus"
            }
            
            val prompt = """
You are an equipment recommendation assistant for a college resource management system.

Project Details:
$projectDescription

Available Equipment in Database:
$equipmentText

Based on the project description, suggest the most suitable equipment from the available list above.

IMPORTANT: You MUST respond with ONLY a valid JSON array. Do not include any markdown, explanations, or text outside the JSON array.

Return a JSON array with this exact structure (use null for missing values):
[
  {
    "equipmentName": "exact equipment name from the list above",
    "buildingName": "building name from the list",
    "buildingNumber": "building number or null",
    "departmentName": "department name from the list",
    "location": "location from the list",
    "status": "Available or Not Available or Waiting",
    "reason": "brief reason why this equipment is suitable"
  }
]

Rules:
- Only suggest equipment that exists in the provided list
- Match equipment names exactly as they appear in the list
- Return 3-5 suggestions if possible
- If status shows "Waiting" in the list, use "Waiting" in the status field
- Return ONLY the JSON array, nothing else
            """.trimIndent()
            
            val request = GeminiRequest(
                contents = listOf(
                    GeminiRequest.Content(
                        parts = listOf(
                            GeminiRequest.Content.Part(text = prompt)
                        )
                    )
                )
            )
            
            val url = "$baseUrl?key=$apiKey"
            Log.d("GeminiApi", "Making request to Gemini API")
            Log.d("GeminiApi", "URL: $url")
            Log.d("GeminiApi", "Equipment count: ${equipmentList.size}")
            
            val response = try {
                httpClient.post(url) {
                    contentType(ContentType.Application.Json)
                    setBody(request)
                }
            } catch (e: Exception) {
                Log.e("GeminiApi", "Network error: ${e.message}", e)
                return@withContext Result.failure(Exception("Network error: ${e.message}"))
            }
            
            val responseBody = response.bodyAsText()
            Log.d("GeminiApi", "Gemini API response received - Status: ${response.status.value}")
            Log.d("GeminiApi", "Response body (first 500 chars): ${responseBody.take(500)}")
            
            if (response.status.value !in 200..299) {
                Log.e("GeminiApi", "Error response: ${response.status.value} - $responseBody")
                return@withContext Result.failure(Exception("Gemini API error (${response.status.value}): ${responseBody.take(200)}"))
            }
            
            // Parse response
            val jsonResponse = Json.parseToJsonElement(responseBody).jsonObject
            val candidates = jsonResponse["candidates"]
            
            if (candidates == null) {
                Log.e("GeminiApi", "No candidates in response")
                return@withContext Result.failure(Exception("No suggestions generated"))
            }
            
            val candidatesArray = candidates.jsonArray
            if (candidatesArray.isEmpty()) {
                Log.e("GeminiApi", "Empty candidates array")
                return@withContext Result.failure(Exception("No suggestions generated"))
            }
            
            val candidate = candidatesArray.first().jsonObject
            val content = candidate["content"]?.jsonObject
            
            if (content == null) {
                Log.e("GeminiApi", "No content in candidate")
                return@withContext Result.failure(Exception("Invalid response format"))
            }
            
            val parts = content["parts"]?.jsonArray
            
            if (parts.isNullOrEmpty()) {
                Log.e("GeminiApi", "No parts in response")
                return@withContext Result.failure(Exception("Invalid response format"))
            }
            
            val text = parts.first().jsonObject["text"]?.jsonPrimitive?.content
                ?: return@withContext Result.failure(Exception("No text in response"))
            
            Log.d("GeminiApi", "Extracted text from response: ${text.take(200)}...")
            
            // Extract JSON array from text (Gemini might wrap it in markdown)
            val jsonStart = text.indexOf('[')
            val jsonEnd = text.lastIndexOf(']') + 1
            
            if (jsonStart == -1 || jsonEnd == 0) {
                Log.e("GeminiApi", "No JSON array found in response")
                return@withContext Result.failure(Exception("Invalid response format: no JSON array found"))
            }
            
            val jsonArrayText = text.substring(jsonStart, jsonEnd)
            Log.d("GeminiApi", "Extracted JSON: $jsonArrayText")
            
            val suggestions = try {
                val parsed = Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                }.decodeFromString<List<AiSuggestion.SuggestedEquipment>>(jsonArrayText)
                Log.d("GeminiApi", "Successfully parsed ${parsed.size} suggestions")
                parsed
            } catch (e: Exception) {
                Log.e("GeminiApi", "Error parsing JSON array", e)
                Log.e("GeminiApi", "JSON text that failed: $jsonArrayText")
                // Try to extract suggestions manually as fallback
                try {
                    val fallbackSuggestions = extractSuggestionsFromText(text, equipmentList)
                    if (fallbackSuggestions.isNotEmpty()) {
                        Log.d("GeminiApi", "Using fallback parsing: ${fallbackSuggestions.size} suggestions")
                        fallbackSuggestions
                    } else {
                        return@withContext Result.failure(Exception("Failed to parse suggestions. Please try again with more details."))
                    }
                } catch (fallbackError: Exception) {
                    Log.e("GeminiApi", "Fallback parsing also failed", fallbackError)
                    return@withContext Result.failure(Exception("Failed to parse suggestions: ${e.message}"))
                }
            }
            
            if (suggestions.isEmpty()) {
                Log.w("GeminiApi", "No suggestions found in response")
                return@withContext Result.failure(Exception("No equipment suggestions found. Please provide more project details."))
            }
            
            Result.success(AiSuggestion(suggestions))
            
        } catch (e: Exception) {
            Log.e("GeminiApi", "Error getting suggestions", e)
            Result.failure(e)
        }
    }
    
    // Fallback method to extract suggestions from text if JSON parsing fails
    private fun extractSuggestionsFromText(
        text: String,
        equipmentList: List<GeminiApiService.EquipmentInfo>
    ): List<AiSuggestion.SuggestedEquipment> {
        val suggestions = mutableListOf<AiSuggestion.SuggestedEquipment>()
        
        // Try to find equipment names in the text and match them with equipment list
        equipmentList.forEach { eq ->
            if (text.contains(eq.name, ignoreCase = true)) {
                val buildingInfo = if (eq.buildingName != null) {
                    "${eq.buildingName}${eq.buildingNumber?.let { " ($it)" } ?: ""}"
                } else {
                    "Unknown Building"
                }
                
                val status = when {
                    eq.requestStatus?.contains("waiting", ignoreCase = true) == true -> "Waiting"
                    eq.requestStatus?.contains("available", ignoreCase = true) == true -> "Available"
                    eq.isAvailable -> "Available"
                    else -> "Not Available"
                }
                
                suggestions.add(
                    AiSuggestion.SuggestedEquipment(
                        equipmentName = eq.name,
                        buildingName = eq.buildingName ?: "Unknown",
                        buildingNumber = eq.buildingNumber,
                        departmentName = eq.departmentName ?: "N/A",
                        location = eq.location ?: "N/A",
                        status = status,
                        reason = "Suggested based on project requirements"
                    )
                )
            }
        }
        
        return suggestions.take(5) // Limit to 5 suggestions
    }
}

