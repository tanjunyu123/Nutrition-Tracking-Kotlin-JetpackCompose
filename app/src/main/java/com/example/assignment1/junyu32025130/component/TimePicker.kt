package com.example.assignment1.junyu32025130.component

import android.app.TimePickerDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import java.util.Calendar

@Composable
fun TimePickerFun(mTime: MutableState<String>): TimePickerDialog {
    // Get the current context
    val mContext = LocalContext.current

    // Get a calendar instance
    val mCalendar = Calendar.getInstance()

    // Get the current hour and minute
    val mHour = mCalendar.get(Calendar.HOUR_OF_DAY)
    val mMinute = mCalendar.get(Calendar.MINUTE)

    // Set the calendar's time to the current time
    mCalendar.time = Calendar.getInstance().time

    // Return a TimePickerDialog
    return TimePickerDialog(
        mContext,
        { _, selectedHour: Int, selectedMinute: Int ->
            mTime.value = String.format("%02d:%02d", selectedHour, selectedMinute)
        },
        mHour, mMinute, false
    )
}