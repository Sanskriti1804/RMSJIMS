package com.example.rmsjims.repository

import com.example.rmsjims.data.schema.Branch
import com.example.rmsjims.data.remote.apiservice.BranchesApiService

class BranchRepository(
    private val branchesApiService: BranchesApiService
){
    suspend fun fetchBranches() : List<Branch>{
        return branchesApiService.getBranches()
    }
}