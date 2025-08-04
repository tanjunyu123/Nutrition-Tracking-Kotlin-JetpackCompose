package com.example.assignment1.junyu32025130.repository

import android.content.Context
import com.example.assignment1.junyu32025130.dao.NutriCoachTipsDao
import com.example.assignment1.junyu32025130.database.AppDatabase
import com.example.assignment1.junyu32025130.entity.NutriCoachTips
import kotlinx.coroutines.flow.Flow

class NutriCoachTipsRepository {

    private var nutriCoachTipsDao : NutriCoachTipsDao
    constructor(context : Context){
        nutriCoachTipsDao = AppDatabase.getDatabase(context).NutriCoachTipsDao()
    }
    suspend fun insertTip(tip: NutriCoachTips) = nutriCoachTipsDao.insertTip(tip)
    fun getAllTipsForUser(userId : Int): Flow<List<NutriCoachTips>> = nutriCoachTipsDao.getAllTipsForUser(userId = userId)
}