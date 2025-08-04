package com.example.assignment1.junyu32025130.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.assignment1.junyu32025130.api.service.GenAiUiState
import com.example.assignment1.junyu32025130.BuildConfig
import com.example.assignment1.junyu32025130.entity.Patient
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GenAiViewModel(context : Context) : ViewModel() {

    private val _uiMotivationalState : MutableStateFlow<GenAiUiState> = MutableStateFlow(GenAiUiState.Initial)

    val uiMotivationalState : StateFlow<GenAiUiState> = _uiMotivationalState.asStateFlow()

    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = BuildConfig.GENAI_API_KEY
    )

    fun sendMotivationalPrompt(prompt : String, onSuccess : (String) -> Unit){
        _uiMotivationalState.value = GenAiUiState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = generativeModel.generateContent (
                    content {
                        text(prompt)
                    }
                )

                response.text?.let {output ->
                    _uiMotivationalState.value = GenAiUiState.Success(output)
                    onSuccess(output)

                }
            } catch(e: Exception){
                _uiMotivationalState.value = GenAiUiState.Error(e.localizedMessage ?: "")
            }
        }
    }

    private val _uiDataPatternState : MutableStateFlow<GenAiUiState> = MutableStateFlow(GenAiUiState.Initial)

    val uiDataPatternState : StateFlow<GenAiUiState> = _uiDataPatternState.asStateFlow()

    fun sendDataPatternPrompt(users : List<Patient>){


        _uiDataPatternState.value = GenAiUiState.Loading
        val gson = Gson()
        val jsonData = gson.toJson(users)
        val prompt = """
                Analyze the following patient dataset and provide 3 unique health-related insights. Each insight should be returned in this exact format:
                (Title): (Insight) | (Title): (Insight) | (Title): (Insight).
    
                Here is the dataset in JSON format :
                $jsonData
    
                Make sure each insight is meaningful, based on patterns or anomalies found across the dataset. Avoid generic health advice.
            
            
        """.trimIndent()


        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = generativeModel.generateContent (
                    content {
                        text(prompt)
                    }
                )

                response.text?.let {output ->
                    _uiDataPatternState.value = GenAiUiState.Success(output)

                }
            } catch(e: Exception){
                _uiDataPatternState.value = GenAiUiState.Error(e.localizedMessage ?: "")
            }
        }
    }


    fun clearState() {
        _uiMotivationalState.value = GenAiUiState.Initial
        _uiDataPatternState.value = GenAiUiState.Initial
    }

    class GenAiViewModelFactory(context : Context) : ViewModelProvider.Factory {
        private val context = context.applicationContext

        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            GenAiViewModel(context) as T

    }
}