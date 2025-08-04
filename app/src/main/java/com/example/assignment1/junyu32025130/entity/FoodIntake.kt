package com.example.assignment1.junyu32025130.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "food_intake",
    foreignKeys = [
        ForeignKey(
            entity = Patient::class,
            parentColumns = ["userId"],
            childColumns = ["id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class FoodIntake(
    @PrimaryKey val id: Int,
    val isFruitsChecked: Boolean,
    val isVegetablesChecked: Boolean,
    val isGrainsChecked: Boolean,
    val isRedMeatChecked: Boolean,
    val isFishChecked: Boolean,
    val isNutsChecked: Boolean,
    val isSeafoodChecked: Boolean,
    val isPoultryChecked: Boolean,
    val isEggsChecked: Boolean,
    val selectedPersona: String,
    val mTimeEat: String,
    val mTimeSleep: String,
    val mTimeWake: String
)