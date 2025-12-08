package com.example.rmsjims.util

import com.example.rmsjims.BuildConfig

object config {
    val SUPABASE_URL: String
        get() = BuildConfig.SUPABASE_URL.takeIf { it.isNotBlank() }
            ?: error("Supabase URL missing. Define SUPABASE_URL in local.properties or environment.")

    val SUPABASE_KEY: String
        get() = BuildConfig.SUPABASE_KEY.takeIf { it.isNotBlank() }
            ?: error("Supabase anon key missing. Define SUPABASE_KEY in local.properties or environment.")
    
    val GEMINI_API_KEY: String
        get() = BuildConfig.GEMINI_API_KEY.takeIf { it.isNotBlank() }
            ?: error("Gemini API key missing. Define GEMINI_API_KEY in local.properties or environment.")
}