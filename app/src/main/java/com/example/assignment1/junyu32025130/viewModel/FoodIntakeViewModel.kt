package com.example.assignment1.junyu32025130.viewModel

import FoodIntakeRepository
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.assignment1.junyu32025130.entity.FoodIntake
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FoodIntakeViewModel(context: Context) : ViewModel() {

    private val foodIntakeRepository = FoodIntakeRepository(context)
    private val _currentUserFoodIntake = MutableStateFlow<FoodIntake?>(null)
    val currentUserFoodIntake: StateFlow<FoodIntake?> = _currentUserFoodIntake

    fun loadFoodIntake(userId: Int) {
        // Launch a coroutine in viewModelScope to load data asynchronously
        viewModelScope.launch {
            foodIntakeRepository.getFoodIntakeByUserId(userId).collect { data ->
                Log.d("data fetched", "loadFoodIntake: $data")
                if (data != null) {
                    Log.d("emit data", "loadFoodIntake: $data")
                    _currentUserFoodIntake.emit(data)
                }
            }
        }
    }

    fun getDefaultFoodIntake(): FoodIntake {
        return FoodIntake(
            id = -1,
            isFruitsChecked = false,
            isVegetablesChecked = false,
            isGrainsChecked = false,
            isRedMeatChecked = false,
            isFishChecked = false,
            isNutsChecked = false,
            isSeafoodChecked = false,
            isPoultryChecked = false,
            isEggsChecked = false,
            selectedPersona = "",
            mTimeEat = "00:00",
            mTimeSleep = "00:00",
            mTimeWake = "00:00"
        )
    }

    fun upsertFoodIntake(foodIntake: FoodIntake) {
        Log.d("save", "upsertFoodIntake: ")
        viewModelScope.launch {
            foodIntakeRepository.upsertFoodIntake(foodIntake)
        }
    }

    fun deleteFoodIntake(foodIntake: FoodIntake) {
        viewModelScope.launch {
            foodIntakeRepository.deleteFoodIntake(foodIntake)
        }
    }

    fun clearState(){
        _currentUserFoodIntake.value = null
    }

    class FoodIntakeViewModelFactory(context : Context) : ViewModelProvider.Factory {
        private val context = context.applicationContext

        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            FoodIntakeViewModel(context) as T

    }
}
