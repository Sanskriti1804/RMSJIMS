package com.example.labinventory.data.remote.api

import com.example.labinventory.data.model.Branch
import com.example.labinventory.data.remote.BranchesApiService
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest

class BranchesApi(
    private val supabaseClient: SupabaseClient
) : BranchesApiService{

    private val table = supabaseClient.postgrest["branches"]
    override suspend fun getBranches(): List<Branch> {
        return table.select().decodeList()
    }
}