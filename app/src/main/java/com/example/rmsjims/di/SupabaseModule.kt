package com.example.rmsjims.di

//SupabaseClient: the core Supabase client you'll inject into your services.
//createSupabaseClient: helper to build and configure the Supabase client.
//Postgrest: plugin that lets you interact with Supabase's database (tables).
//module: part of Koin DSL to define dependencies.

import android.util.Log
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

import org.koin.dsl.module

//efining a Koin module named supabaseModule that takes your Supabase project's URL and Key as parameters.
fun supabaseModule(
    supabaseUrl: String,
    supabaseKey: String
) = module {
    single<SupabaseClient> {        //singleton
        Log.d("SupabaseModule", "Creating Supabase client...")
        Log.d("SupabaseModule", "URL: $supabaseUrl")
        Log.d("SupabaseModule", "Key prefix: ${supabaseKey.take(20)}...")
        try {
            val client = createSupabaseClient(
                supabaseUrl = supabaseUrl,
                supabaseKey = supabaseKey
            ){
                install(Postgrest)  //install the PostgREST plugin to query tables like a REST API
            }
            Log.d("SupabaseModule", "Supabase client created successfully")
            client
        } catch (e: Exception) {
            Log.e("SupabaseModule", "FAILED to create Supabase client", e)
            Log.e("SupabaseModule", "Exception type: ${e.javaClass.simpleName}")
            Log.e("SupabaseModule", "Exception message: ${e.message}")
            throw e
        }
    }
}
