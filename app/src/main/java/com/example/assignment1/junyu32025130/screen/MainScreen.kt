package com.example.assignment1.junyu32025130.screen

import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.assignment1.junyu32025130.component.CustomBottomAppBar
import com.example.assignment1.junyu32025130.viewModel.FoodIntakeViewModel
import com.example.assignment1.junyu32025130.viewModel.FruitViewModel
import com.example.assignment1.junyu32025130.viewModel.GenAiViewModel
import com.example.assignment1.junyu32025130.viewModel.NutriCoachTipsViewModel
import com.example.assignment1.junyu32025130.viewModel.PatientViewModel

@Composable
fun MainScreen(patientViewModel : PatientViewModel, fruitViewModel: FruitViewModel, foodIntakeModel : FoodIntakeViewModel, genAiViewModel: GenAiViewModel, nutriCoachTipsViewModel : NutriCoachTipsViewModel, navController : NavHostController) {
    val bottomNavController = rememberNavController()
    val context = LocalContext.current
    Scaffold(
        bottomBar = {
            CustomBottomAppBar(bottomNavController)
        }
    ) { innerPadding ->
        NavHost(
            navController = bottomNavController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") {
                HomeScreen(patientViewModel, navToQuestionnaire = {navController.navigate("questionnaire") }){
                    bottomNavController.navigate("insight")
                }
            }
            composable("insight") {
                InsightScreen(patientViewModel){
                    bottomNavController.navigate("nutricoach")
                }
            }
            composable("nutricoach") {
                NutriCoachScreen(patientViewModel,fruitViewModel,genAiViewModel,nutriCoachTipsViewModel)
            }
            composable("settings") {
                SettingsScreen(patientViewModel,genAiViewModel){
                    // clear all states before log out
                    patientViewModel.clearState()
                    fruitViewModel.clearState()
                    foodIntakeModel.clearState()
                    genAiViewModel.clearState()
                    nutriCoachTipsViewModel.clearState()

                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true } // Clears the backstack
                    }

                    Toast.makeText(context,"Logged out successfully", Toast.LENGTH_LONG ).show()
                }
            }

        }
    }
}