package com.example.assignment1.junyu32025130.component

import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Insights
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Settings

import androidx.compose.material3.Icon

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.toLowerCase

import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun CustomBottomAppBar(navController: NavController) {
    val items = listOf(
        "Home" to Icons.Filled.Home,
        "Insight" to Icons.Filled.Insights,
        "NutriCoach" to Icons.Filled.Psychology,
        "Settings" to Icons.Filled.Settings,

    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = Color.White,
    ) {
        items.forEachIndexed { index, (route, icon) ->
            NavigationBarItem(
                icon = { Icon(icon, contentDescription = route) },
                label = { Text(route) },
                selected = currentDestination == route.toLowerCase(),
                onClick = {
                    if (currentDestination != route) {
                        navController.navigate(route.toLowerCase()) {
                            popUpTo("home") { inclusive = false }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}

