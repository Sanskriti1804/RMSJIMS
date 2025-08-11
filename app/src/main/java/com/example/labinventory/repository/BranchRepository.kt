package com.example.labinventory.repository

import com.example.labinventory.data.schema.Branch
import com.example.labinventory.data.remote.BranchesApiService

class BranchRepository(
    private val branchesApiService: BranchesApiService
){
    suspend fun fetchBranches() : List<Branch>{
        return branchesApiService.getBranches()
    }
}