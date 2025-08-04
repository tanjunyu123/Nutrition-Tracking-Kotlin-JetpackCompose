package com.example.assignment1.junyu32025130.viewModel

import FoodIntakeRepository
import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.assignment1.junyu32025130.entity.FoodIntake
import com.example.assignment1.junyu32025130.repository.PatientRepository
import com.example.assignment1.junyu32025130.entity.Patient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import parseCsvToUserData


class PatientViewModel(context : Context) : ViewModel() {

    private val patientRepository = PatientRepository(context)


    val allUsers : Flow<List<Patient>> = patientRepository.getAllPatients()

    private val _currentPatientData = mutableStateOf<Patient?>(null)
    val currentPatientData: State<Patient?> = _currentPatientData

    fun loadCurrentPatientData(userId: Int) {
        viewModelScope.launch {
            patientRepository.getPatientById(userId).let { data ->
                if (data != null) {
                    _currentPatientData.value = data
                }
            }
        }
    }
    fun insertPatient(patient: Patient) {
        viewModelScope.launch {
            patientRepository.insertPatient(patient)
        }
    }


    fun updatePatient(patient: Patient) {
        viewModelScope.launch {
            patientRepository.updatePatient(patient)
        }
    }

    fun deletePatient(patient: Patient) {
        viewModelScope.launch {
            patientRepository.deletePatient(patient)
        }
    }

    fun updatePassword(id: Int, password: String) {

        viewModelScope.launch {
            patientRepository.updatePassword(id,password)
        }
    }

    fun initializeDatabaseIfFirstLaunch(context: Context, fileName: String) {
        val sharedPref = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val isFirstLaunch = sharedPref.getBoolean("isFirstLaunch", true)

        if (isFirstLaunch) {
            viewModelScope.launch {
                val userList = parseCsvToUserData(context, fileName)
                patientRepository.insertAllPatients(userList)
                sharedPref.edit().putBoolean("isFirstLaunch", false).apply()
            }
        }
    }

    fun clearState(){
        _currentPatientData.value = null
    }

    class PatientViewModelFactory(context : Context) : ViewModelProvider.Factory {
        private val context = context.applicationContext

        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            PatientViewModel(context) as T

    }
}