package com.example.assignment1.junyu32025130.api.service

sealed interface  GenAiUiState {
    object Initial : GenAiUiState

    object Loading : GenAiUiState

    data class Success(val outputText : String) : GenAiUiState

    data class Error(val errorMessage : String) : GenAiUiState
}