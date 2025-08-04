package com.example.assignment1.junyu32025130.viewModel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.assignment1.junyu32025130.entity.NutriCoachTips
import com.example.assignment1.junyu32025130.repository.NutriCoachTipsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NutriCoachTipsViewModel(context : Context) : ViewModel() {
    private val repository = NutriCoachTipsRepository(context)

    private val _allTips = MutableStateFlow<List<NutriCoachTips>>(emptyList())
    val allTips: StateFlow<List<NutriCoachTips>> = _allTips
    val loaded = mutableStateOf(false)
    fun loadUserTips(context: Context) {
        viewModelScope.launch {
            val currentUserPref = context.getSharedPreferences("currentUser", Context.MODE_PRIVATE)
            val userId = currentUserPref.getString("userId", "")?.toIntOrNull()

            userId?.let {
                repository.getAllTipsForUser(it).collect { tips ->
                    _allTips.value = tips
                }

                loaded.value = true
            }
        }
    }


    fun insertTip( userId : Int, prompt: String, response: String) {
        viewModelScope.launch {
            repository.insertTip(NutriCoachTips(userId = userId ,prompt = prompt, response = response))
        }
    }

    fun clearState(){
        _allTips.value = emptyList()
        loaded.value = false
    }

    class NutriCoachTipsViewModelFactory(context : Context) : ViewModelProvider.Factory {
        private val context = context.applicationContext

        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            NutriCoachTipsViewModel(context) as T

    }
}