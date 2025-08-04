package com.example.assignment1.junyu32025130.util

import java.time.LocalTime
import java.time.format.DateTimeFormatter

// Convert a time string (e.g., "14:30") to LocalTime object
fun String.toLocalTime(): LocalTime {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")  // Assuming time format is "HH:mm"
    return LocalTime.parse(this, formatter)
}

// Validate the times
fun validateTimes(eatTime: String, sleepTime: String, wakeTime: String): Boolean {
    val eat = LocalTime.parse(eatTime)
    val sleep = LocalTime.parse(sleepTime)
    val wake = LocalTime.parse(wakeTime)

    return if (sleep.isBefore(wake)) {
        // Normal: same day
        !(eat >= sleep && eat < wake)
    } else {
        // Overnight case: sleep is before midnight, wake is after midnight
        // Invalid if eat is between sleep...midnight or midnight...wake
        !(eat >= sleep || eat < wake)
    }
}
