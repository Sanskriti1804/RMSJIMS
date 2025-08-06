package com.example.labinventory.repository

import com.example.labinventory.data.model.Branch
import com.example.labinventory.data.remote.BranchesApiService
import com.example.labinventory.data.remote.api.BranchesApi

class BranchRepository(
    private val branchesApiService: BranchesApiService
){
    suspend fun fetchBranches() : List<Branch>{
        return branchesApiService.getBranches()
    }
}