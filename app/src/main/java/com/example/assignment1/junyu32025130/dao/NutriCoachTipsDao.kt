package com.example.assignment1.junyu32025130.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.assignment1.junyu32025130.entity.NutriCoachTips
import kotlinx.coroutines.flow.Flow

@Dao
interface NutriCoachTipsDao {
    @Insert
    suspend fun insertTip(tip: NutriCoachTips)

    @Query("SELECT * FROM nutri_coach_tips WHERE userId = :userId ORDER BY timestamp DESC")
    fun getAllTipsForUser(userId: Int): Flow<List<NutriCoachTips>>
}