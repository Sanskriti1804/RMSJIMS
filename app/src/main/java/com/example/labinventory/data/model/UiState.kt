package com.example.labinventory.data.model

// Sealed classes are used to represent restricted class hierarchies, ensuring that all possible states are known at compile time.
sealed class UiState<out T>{
    object Loading : UiState<Nothing>()
    //data class as they hold data
    data class Success<out T>(val data: T) : UiState<T>() //T - as it only carries some type of data rest dont
    data class Error(val exception: Throwable) : UiState<Nothing>()
}