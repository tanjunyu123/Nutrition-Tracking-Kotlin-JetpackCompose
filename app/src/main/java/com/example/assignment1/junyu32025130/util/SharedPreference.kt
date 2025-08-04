package com.example.assignment1.junyu32025130.util

import android.content.Context
import android.content.SharedPreferences
//import com.example.assignment1.junyu32025130.entity.UserPreferencesData
//
//fun saveToSharedPreferences(context: Context,
//                            isFruitsChecked: Boolean,
//                            isVegetablesChecked: Boolean,
//                            isGrainsChecked: Boolean,
//                            isRedMeatChecked: Boolean,
//                            isFishChecked: Boolean,
//                            isNutsChecked: Boolean,
//                            isSeafoodChecked: Boolean,
//                            isPoultryChecked: Boolean,
//                            isEggsChecked: Boolean,
//                            selectedPersona: String,
//                            mTimeEat: String,
//                            mTimeSleep: String,
//                            mTimeWake: String) {
//    val currentUserPref = context.getSharedPreferences("currentUser", Context.MODE_PRIVATE)
//    val userId = currentUserPref.getString("userId","")
//
//    val sharedPreferences: SharedPreferences = context.getSharedPreferences("UserPreferences_$userId", Context.MODE_PRIVATE)
//    val editor = sharedPreferences.edit()
//
//    editor.putBoolean("isFruitsChecked", isFruitsChecked)
//    editor.putBoolean("isVegetablesChecked", isVegetablesChecked)
//    editor.putBoolean("isGrainsChecked", isGrainsChecked)
//    editor.putBoolean("isRedMeatChecked", isRedMeatChecked)
//    editor.putBoolean("isFishChecked", isFishChecked)
//    editor.putBoolean("isNutsChecked", isNutsChecked)
//    editor.putBoolean("isSeafoodChecked", isSeafoodChecked)
//    editor.putBoolean("isPoultryChecked", isPoultryChecked)
//    editor.putBoolean("isEggsChecked", isEggsChecked)
//    editor.putString("selectedPersona", selectedPersona)
//    editor.putString("mTimeEat", mTimeEat)
//    editor.putString("mTimeSleep", mTimeSleep)
//    editor.putString("mTimeWake", mTimeWake)
//
//    editor.apply()
//}

//fun loadUserPreferences(context: Context): UserPreferencesData {
//    val currentUserPref = context.getSharedPreferences("currentUser", Context.MODE_PRIVATE)
//    val userId = currentUserPref.getString("userId","")
//
//    val sharedPreferences = context.getSharedPreferences("UserPreferences_$userId", Context.MODE_PRIVATE)
//
//    return UserPreferencesData(
//        isFruitsChecked = sharedPreferences.getBoolean("isFruitsChecked", false),
//        isVegetablesChecked = sharedPreferences.getBoolean("isVegetablesChecked", false),
//        isGrainsChecked = sharedPreferences.getBoolean("isGrainsChecked", false),
//        isRedMeatChecked = sharedPreferences.getBoolean("isRedMeatChecked", false),
//        isFishChecked = sharedPreferences.getBoolean("isFishChecked", false),
//        isNutsChecked = sharedPreferences.getBoolean("isNutsChecked", false),
//        isSeafoodChecked = sharedPreferences.getBoolean("isSeafoodChecked", false),
//        isPoultryChecked = sharedPreferences.getBoolean("isPoultryChecked", false),
//        isEggsChecked = sharedPreferences.getBoolean("isEggsChecked", false),
//        selectedPersona = sharedPreferences.getString("selectedPersona", "") ?: "",
//        mTimeEat = sharedPreferences.getString("mTimeEat", "00:00") ?: "00:00",
//        mTimeSleep = sharedPreferences.getString("mTimeSleep", "00:00") ?: "00:00",
//        mTimeWake = sharedPreferences.getString("mTimeWake", "00:00") ?: "00:00"
//    )
//}