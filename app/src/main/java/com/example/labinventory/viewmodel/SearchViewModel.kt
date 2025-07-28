package com.example.labinventory.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SearchViewModel : ViewModel() {

    private val _query = mutableStateOf("")
    val query = _query

    fun onQueryChange(newValue: String) {
        _query.value = newValue
    }

    fun onSearchTriggered() {
        println("Search triggered with query: ${_query.value}")
    }
}
