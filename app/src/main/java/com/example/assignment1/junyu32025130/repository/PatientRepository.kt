package com.example.assignment1.junyu32025130.repository

import android.content.Context
import com.example.assignment1.junyu32025130.dao.PatientDao
import com.example.assignment1.junyu32025130.database.AppDatabase
import com.example.assignment1.junyu32025130.entity.Patient
import kotlinx.coroutines.flow.Flow

class PatientRepository {

    private var patientDao: PatientDao


    constructor(context : Context){
        patientDao = AppDatabase.getDatabase(context).PatientDao()
    }
    suspend fun insertPatient(patient: Patient) {
        patientDao.insertPatient(patient)
    }

    suspend fun getPatientById(userId: Int): Patient? {
        return patientDao.getPatientById(userId)
    }

    fun getAllPatients(): Flow<List<Patient>> {
        return patientDao.getAllPatients()
    }

    suspend fun updatePassword(id: Int, password: String) {
        return patientDao.updatePassword(id,password)
    }
    suspend fun updatePatient(patient: Patient) {
        patientDao.updatePatient(patient)
    }

    suspend fun deletePatient(patient: Patient) {
        patientDao.deletePatient(patient)
    }

    suspend fun insertAllPatients(users: List<Patient>) {
        patientDao.insertAllPatients(users)
    }
}