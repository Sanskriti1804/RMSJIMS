package com.example.rmsjims.data.remote.apiservice

import com.example.rmsjims.data.schema.Branch

interface BranchesApiService {
    suspend fun getBranches() : List<Branch>
}