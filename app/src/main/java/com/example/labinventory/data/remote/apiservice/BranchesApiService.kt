package com.example.labinventory.data.remote.apiservice

import com.example.labinventory.data.schema.Branch

interface BranchesApiService {
    suspend fun getBranches() : List<Branch>
}