package com.example.rmsjims.data.remote.api

import com.example.rmsjims.data.schema.Department
import com.example.rmsjims.data.remote.apiservice.DepartmentApiService
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest

class DepartmentApi(
    private val supabaseClient: SupabaseClient
) : DepartmentApiService {

    private val table = supabaseClient.postgrest["departments"]
    override suspend fun getDepartments(): List<Department> {
        return table.select().decodeList()
    }
}
