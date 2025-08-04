package com.example.assignment1.junyu32025130

import WelcomeScreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.foundation.layout.padding

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.assignment1.junyu32025130.component.CustomTopAppBar
import com.example.assignment1.junyu32025130.screen.LoginScreen
import com.example.assignment1.junyu32025130.screen.MainScreen
import com.example.assignment1.junyu32025130.screen.QuestionnaireScreen
import com.example.assignment1.junyu32025130.screen.RegisterScreen
import com.example.assignment1.junyu32025130.screen.StartScreen

import com.example.assignment1.junyu32025130.ui.theme.Assignment1Theme
import com.example.assignment1.junyu32025130.viewModel.FoodIntakeViewModel
import com.example.assignment1.junyu32025130.viewModel.FruitViewModel
import com.example.assignment1.junyu32025130.viewModel.GenAiViewModel
import com.example.assignment1.junyu32025130.viewModel.NutriCoachTipsViewModel
import com.example.assignment1.junyu32025130.viewModel.PatientViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Assignment1Theme {

                // Use of LocalContext in Composables
                val context = LocalContext.current
                val patientViewModel: PatientViewModel = ViewModelProvider(
                    this@MainActivity, PatientViewModel.PatientViewModelFactory(this@MainActivity)
                )[PatientViewModel::class.java]

                val foodIntakeViewModel: FoodIntakeViewModel = ViewModelProvider(
                    this@MainActivity, FoodIntakeViewModel.FoodIntakeViewModelFactory(this@MainActivity)
                )[FoodIntakeViewModel::class.java]

                val fruitViewModel: FruitViewModel = ViewModelProvider(
                    this@MainActivity, FruitViewModel.FruitViewModelFactory(this@MainActivity)
                )[FruitViewModel::class.java]

                val genAiViewModel: GenAiViewModel = ViewModelProvider(
                    this@MainActivity, GenAiViewModel.GenAiViewModelFactory(this@MainActivity)
                )[GenAiViewModel::class.java]

                val nutriCoachTipsViewModel: NutriCoachTipsViewModel = ViewModelProvider(
                    this@MainActivity, NutriCoachTipsViewModel.NutriCoachTipsViewModelFactory(this@MainActivity)
                )[NutriCoachTipsViewModel::class.java]


                // Call initializeDatabaseIfFirstLaunch inside a LaunchedEffect so it happens in composable scope
                LaunchedEffect(Unit) {
                    patientViewModel.initializeDatabaseIfFirstLaunch(context, "user.csv")
                }

                // Pass ViewModels to NavGraph
                MainNavGraph(patientViewModel, foodIntakeViewModel,fruitViewModel,genAiViewModel,nutriCoachTipsViewModel)
            }
        }
    }
}
@Composable
fun MainNavGraph(patientViewModel: PatientViewModel,foodIntakeViewModel: FoodIntakeViewModel,fruitViewModel : FruitViewModel,genAiViewModel: GenAiViewModel,nutriCoachTipsViewModel : NutriCoachTipsViewModel) {
    val navController = rememberNavController()



    NavHost(navController = navController, startDestination = "start") {

        composable("start") {
            StartScreen(navController)
        }


        composable("welcome") {
            WelcomeScreen(onNext = { navController.navigate("login") })
        }
        composable("login") {
            LoginScreen(patientViewModel,onLoginSuccess = { navController.navigate("questionnaire") }, onRegister = {navController.navigate("register")} ){
                // clear all states before log out
                patientViewModel.clearState()
                fruitViewModel.clearState()
                foodIntakeViewModel.clearState()
                genAiViewModel.clearState()
                nutriCoachTipsViewModel.clearState()
            }
        }
        composable("questionnaire") {
            Scaffold(
                topBar = {
                    CustomTopAppBar(title = "Questionnaire"){
                        navController.navigate("login") {
                            // clear all states before log out
                            patientViewModel.clearState()
                            fruitViewModel.clearState()
                            foodIntakeViewModel.clearState()
                            genAiViewModel.clearState()
                            nutriCoachTipsViewModel.clearState()

                            popUpTo(0) { inclusive = true } // Clears the entire back stack
                            launchSingleTop = true
                        }
                    }
                }
            ) {
                QuestionnaireScreen(foodIntakeViewModel,Modifier.padding(it) ,onDone = { navController.navigate("main"){
                    popUpTo(0) { inclusive = true } // Clears the entire back stack
                    launchSingleTop = true
                } })
            }

        }

        composable("register") {
            RegisterScreen(patientViewModel, onRegisterSucess = { navController.navigate("login") }){
                navController.navigate("login")
            }
        }
        composable("main") {
            MainScreen(patientViewModel,fruitViewModel,foodIntakeViewModel,genAiViewModel ,nutriCoachTipsViewModel,navController)
        }
    }
}


