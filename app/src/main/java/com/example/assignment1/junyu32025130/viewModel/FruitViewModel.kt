package com.example.assignment1.junyu32025130.viewModel

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.assignment1.junyu32025130.api.client.RetrofitClient
import com.example.assignment1.junyu32025130.api.model.Fruit
import kotlinx.coroutines.launch
import retrofit2.await

class FruitViewModel(context: Context) : ViewModel() {

    private val _fruit = mutableStateOf<Fruit?>(null)
    val fruit: State<Fruit?> = _fruit
    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading



    fun getFruitInfo(fruitName : String, onSuccess : () -> Unit ,onError : (String) -> Unit) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val fruitList = RetrofitClient.apiService.getFruitsByName(fruitName)
                _fruit.value = fruitList.await()

                onSuccess()
            } catch (e: Exception) {
                onError("Failed to fetch fruits: ${e.message}")

            }finally {
                _isLoading.value = false
            }
        }
    }

    fun clearState(){
        _fruit.value = null
        _isLoading.value = false
    }

    class FruitViewModelFactory(context : Context) : ViewModelProvider.Factory {
        private val context = context.applicationContext

        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            FruitViewModel(context) as T

    }
}