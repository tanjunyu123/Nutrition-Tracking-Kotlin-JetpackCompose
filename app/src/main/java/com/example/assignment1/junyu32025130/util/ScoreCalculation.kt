package com.example.assignment1.junyu32025130.util

import com.example.assignment1.junyu32025130.entity.Patient

fun calculateAverageScore(isMale: Boolean, users: List<Patient>): Double {
    val filteredUsers = if (isMale) {
        users.filter { it.sex.equals("Male", ignoreCase = true) }
    } else {
        users.filter { it.sex.equals("Female", ignoreCase = true) }
    }

    return if (filteredUsers.isNotEmpty()) {
        if (isMale) {
            filteredUsers.map { it.heifaTotalScoreMale }.average()
        } else {
            filteredUsers.map { it.heifaTotalScoreFemale }.average()
        }
    } else {
        0.0 // return 0.0 if no matching users
    }
}