package com.example.assignment1.junyu32025130.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(
    tableName = "nutri_coach_tips",
    foreignKeys = [
        ForeignKey(
            entity = Patient::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class NutriCoachTips(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,  // add this field
    val prompt: String,
    val response: String,
    val timestamp: Long = System.currentTimeMillis()
)