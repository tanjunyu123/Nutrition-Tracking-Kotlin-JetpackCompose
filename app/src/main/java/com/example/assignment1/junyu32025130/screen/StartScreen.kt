package com.example.assignment1.junyu32025130.screen

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController

@Composable
fun StartScreen(navController: NavHostController) {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("currentUser", Context.MODE_PRIVATE)
    val userId = prefs.getString("userId", "-1") // default to -1 indicating not logged in

    LaunchedEffect(Unit) {
        if (userId != "-1") { // if logged in
            navController.navigate("main") {
                popUpTo("start") { inclusive = true }
            }
        } else {
            navController.navigate("welcome") {
                popUpTo("start") { inclusive = true }
            }
        }
    }

    // Optional loading UI while deciding
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}