package com.example.rmsjims.data.remote.api

import com.example.rmsjims.data.remote.apiservice.FacilitesApiService
import com.example.rmsjims.data.schema.Facilities
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest

class FacilitiesApi(
    private val supabaseClient: SupabaseClient
) : FacilitesApiService {
    private val table = supabaseClient.postgrest["facilities"]
    override suspend fun getFacilities(): List<Facilities> {
        return table.select().decodeList()
    }
}