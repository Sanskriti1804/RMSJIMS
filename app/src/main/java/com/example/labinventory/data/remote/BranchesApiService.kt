package com.example.labinventory.data.remote

import com.example.labinventory.data.model.Branch

interface BranchesApiService {
    suspend fun getBranches() : List<Branch>
}