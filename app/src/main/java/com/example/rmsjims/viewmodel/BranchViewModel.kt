package com.example.rmsjims.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rmsjims.data.schema.Branch
import com.example.rmsjims.data.model.UiState
import com.example.rmsjims.repository.BranchRepository
import kotlinx.coroutines.launch

class BranchViewModel(
    private val branchRepository: BranchRepository
) : ViewModel() {

    var branchState by mutableStateOf<UiState<List<Branch>>>(UiState.Loading)
        private set

    //data - Access the data property of the UiState.Success object.
    //.map { it.name } - If data is a list, map each element to its name(branch data class).
    val branchName : List<String> get()
            = when(val state = branchState){
                is UiState.Success -> state.data.map { it.name }
            else -> emptyList()
            }

    init {
        getBranches()
    }

    private fun getBranches(){
        viewModelScope.launch {
            branchState = UiState.Loading
            try {
                val branches = branchRepository.fetchBranches()
                branchState = UiState.Success(branches)
            }
            catch (e : Exception){
                branchState = UiState.Error(e)
            }
        }
    }

}
