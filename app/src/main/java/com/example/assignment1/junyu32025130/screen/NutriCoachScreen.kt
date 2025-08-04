package com.example.assignment1.junyu32025130.screen
import androidx.compose.ui.platform.LocalSoftwareKeyboardController

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.assignment1.junyu32025130.api.service.GenAiUiState
import com.example.assignment1.junyu32025130.viewModel.FruitViewModel
import com.example.assignment1.junyu32025130.viewModel.GenAiViewModel
import com.example.assignment1.junyu32025130.viewModel.NutriCoachTipsViewModel
import com.example.assignment1.junyu32025130.viewModel.PatientViewModel

@Composable
fun NutriCoachScreen(patientViewModel: PatientViewModel, fruitViewModel: FruitViewModel , genAiViewModel: GenAiViewModel ,nutriCoachTipsViewModel : NutriCoachTipsViewModel) {
    val context = LocalContext.current
    var fruitName by rememberSaveable { mutableStateOf("") }

    // Mutable state variables for each nutrient
    var family by rememberSaveable { mutableStateOf("") }
    var calories by rememberSaveable { mutableStateOf("") }
    var fat by rememberSaveable { mutableStateOf("") }
    var sugar by rememberSaveable { mutableStateOf("") }
    var carbohydrates by rememberSaveable { mutableStateOf("") }
    var protein by rememberSaveable { mutableStateOf("") }

    // Gen AI UI state
    val genAiState by genAiViewModel.uiMotivationalState.collectAsState()

    // Gen AI response mutable state
    var prompt by rememberSaveable { mutableStateOf("")}
    var result by rememberSaveable { mutableStateOf("") }

    // List of responses
    val responses = nutriCoachTipsViewModel.allTips.collectAsState(initial = emptyList())
    val insertedResult = rememberSaveable { mutableStateOf("") } // state to check if the result was inserted before to avoid orientation changes from inserting same result again
    var tipsDialog by rememberSaveable {
        mutableStateOf(false)
    }
    // Current User
    val currentUser by patientViewModel.currentPatientData

    val keyboardController = LocalSoftwareKeyboardController.current
    LaunchedEffect(Unit) {
        // if other screens already loaded current user before , dont have to fetch user data from db again
        if (currentUser == null) {
            val currentUserPref = context.getSharedPreferences("currentUser", Context.MODE_PRIVATE)
            val userId = currentUserPref.getString("userId", "")?.toIntOrNull()
            if (userId != null) {
                patientViewModel.loadCurrentPatientData(userId)
            }
        }

        // If not loaded tips before , load it
        if(!nutriCoachTipsViewModel.loaded.value){
            nutriCoachTipsViewModel.loadUserTips(context)
        }


    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "NutriCoach",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )



            currentUser?.let { user ->
                if (user.fruitServeSize < 2 || user.fruitVariationsScore == 0.0) {
                    // Fruit Name Input and Details Button
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = fruitName,
                            onValueChange = { fruitName = it },
                            label = { Text("Fruit Name") },
                            modifier = Modifier.weight(1f),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = {
                                keyboardController?.hide()
                                fruitViewModel.getFruitInfo(
                                    fruitName.lowercase().replaceFirstChar { it.uppercase() }
                                        .trim(), {
                                        if (fruitViewModel.fruit.value != null) {
                                            family = fruitViewModel.fruit.value?.family ?: "N/A"
                                            calories =
                                                fruitViewModel.fruit.value?.nutritions?.calories
                                                    ?: "N/A"
                                            fat =
                                                fruitViewModel.fruit.value?.nutritions?.fat ?: "N/A"
                                            sugar = fruitViewModel.fruit.value?.nutritions?.sugar
                                                ?: "N/A"
                                            carbohydrates =
                                                fruitViewModel.fruit.value?.nutritions?.carbohydrates
                                                    ?: "N/A"
                                            protein =
                                                fruitViewModel.fruit.value?.nutritions?.protein
                                                    ?: "N/A"
                                        }

                                    }) { error ->

                                    family = ""
                                    calories = ""
                                    fat = ""
                                    sugar = ""
                                    carbohydrates = ""
                                    protein = ""

                                    Toast.makeText(context, error, Toast.LENGTH_LONG).show()
                                }


                            }
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = null,
                                    tint = Color.White // match button text color
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Details")
                            }

                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    if (fruitViewModel.isLoading.value) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize(), // Occupy full screen
                            contentAlignment = Alignment.Center // Center the spinner
                        ) {
                            CircularProgressIndicator()
                        }
                    } else {
                        // Bind mutable states to rows
                        NutrientRow("family", family)
                        NutrientRow("calories", calories)
                        NutrientRow("fat", fat)
                        NutrientRow("sugar", sugar)
                        NutrientRow("carbohydrates", carbohydrates)
                        NutrientRow("protein", protein)

                    }

                    Spacer(modifier = Modifier.height(16.dp))


                } else {
                    AsyncImage(
                        model = "https://picsum.photos/400/300", // 400x300 random image
                        contentDescription = "Random Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                    )
                }

            }



            Button(
                onClick = {
                    currentUser?.let { user ->
                        // remove the insertedResult for new result coming in. this state is just used to check if same result was inserted when orientation changes so it doesnt insert again
                        insertedResult.value = ""

                        val fruitScore = if (user.sex == "Male") user.fruitHeifaScoreMale else user.fruitHeifaScoreFemale
                        val fruitVariation = if (user.fruitVariationsScore == 0.0) "less than 2" else "at least 2"

                        val additionalInformation = "The person is a ${user.sex} with a HEIFA fruit score of $fruitScore. " +
                                "The criteria for max score is serving of greater or equal to 2 and at least 2 varieties of fruits consumed. " +
                                "The person had serving of ${user.fruitServeSize} and also had $fruitVariation types of fruit."

                        prompt = "Generate a short encouraging message to help someone improve their fruit intake. $additionalInformation. Do not include any placeholder for unknown values like Hey [Name]"

                        genAiViewModel.sendMotivationalPrompt(prompt){output ->
                            nutriCoachTipsViewModel.insertTip(userId = user.userId, prompt = prompt, response =output )
                        }
                    }

                },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.ChatBubbleOutline,
                        contentDescription = null,
                        tint = Color.White // match button text color
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Motivational Message (AI)",
                        color = Color.White,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            currentUser?.let { user ->
                if (genAiState is GenAiUiState.Loading) {
                    // Display loading indicator when content is being generated
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                } else {
                    // Initialize text color with default value
                    var textColor = MaterialTheme.colorScheme.onSurface

                    // Handle error state
                    if (genAiState is GenAiUiState.Error) {
                        // Use error color for error messages
                        textColor = MaterialTheme.colorScheme.error
                        // Update result with the error message
                        result = (genAiState as GenAiUiState.Error).errorMessage
                    }
                    // Handle success state
                    else if (genAiState is GenAiUiState.Success) {
                        // Use default text color for success
                        textColor = MaterialTheme.colorScheme.onSurface
                        // Update result with the generated content
                        result = (genAiState as GenAiUiState.Success).outputText

                    }


                    Text(text = result, textAlign = TextAlign.Start,modifier = Modifier
                        .fillMaxWidth()
                        , color = textColor)
                }

            }




            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                // Show All Tips Button
                Button(
                    onClick = { tipsDialog = true},
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(50.dp),
                ) {
                    Text("Shows All Tips")
                }
            }



        if (tipsDialog) {
            AlertDialog(
                onDismissRequest = { tipsDialog = false },
                title = {
                    Text(text = "NutriCoach Tips")
                },
                text = {
                    // Scrollable list of responses
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 100.dp, max = 400.dp) // limit height for scrolling
                            .verticalScroll(rememberScrollState())
                    ) {
                        responses.value.forEachIndexed { index, response ->

                            Card(modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp) ,  elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)){
                                Text(
                                    text = "${index + 1}. ${response.response}",
                                    modifier = Modifier.padding(10.dp)
                                )
                            }


                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = { tipsDialog = false }) {
                        Text("Close")
                    }
                }
            )
        }
    }


}

@Composable
fun NutrientRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .border(1.dp, Color.LightGray, RoundedCornerShape(4.dp))
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("$label :", modifier = Modifier.weight(1f))
        Text(value, modifier = Modifier.weight(1f))
    }
}
