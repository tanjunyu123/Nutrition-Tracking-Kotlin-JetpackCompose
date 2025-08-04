package com.example.assignment1.junyu32025130.screen


import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Rocket
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.assignment1.junyu32025130.viewModel.PatientViewModel


@Composable
fun InsightScreen( patientViewModel: PatientViewModel,modifier: Modifier = Modifier , navToNutriCoach : () -> Unit) {
    val context = LocalContext.current
    val maxValCategory = 10
    val currentUser  by patientViewModel.currentPatientData

    LaunchedEffect(Unit) {
        // if other screens already loaded current user before , dont have to fetch user data from db again
        if(currentUser == null){
            val currentUserPref = context.getSharedPreferences("currentUser", Context.MODE_PRIVATE)
            val userId = currentUserPref.getString("userId", "")?.toIntOrNull()
            if (userId != null) {
                patientViewModel.loadCurrentPatientData(userId)
            }
        }

    }

    currentUser?.let { user ->
        // Collect all scores once here
        val vegScore = if (user.sex == "Male") user.vegetablesHeifaScoreMale else user.vegetablesHeifaScoreFemale
        val fruitScore = if (user.sex == "Male") user.fruitHeifaScoreMale else user.fruitHeifaScoreFemale
        val grainsScore = if (user.sex == "Male") user.grainsAndCerealsHeifaScoreMale else user.grainsAndCerealsHeifaScoreFemale
        val wholeGrainsScore = if (user.sex == "Male") user.wholeGrainsHeifaScoreMale else user.wholeGrainsHeifaScoreFemale
        val meatScore = if (user.sex == "Male") user.meatAndAlternativesHeifaScoreMale else user.meatAndAlternativesHeifaScoreFemale
        val dairyScore = if (user.sex == "Male") user.dairyAndAlternativesHeifaScoreMale else user.dairyAndAlternativesHeifaScoreFemale
        val waterScore = if (user.sex == "Male") user.waterHeifaScoreMale else user.waterHeifaScoreFemale
        val unsaturatedFatScore = if (user.sex == "Male") user.unsaturatedFatHeifaScoreMale else user.unsaturatedFatHeifaScoreFemale
        val sodiumScore = if (user.sex == "Male") user.sodiumHeifaScoreMale else user.sodiumHeifaScoreFemale
        val sugarScore = if (user.sex == "Male") user.sugarHeifaScoreMale else user.sugarHeifaScoreFemale
        val alcoholScore = if (user.sex == "Male") user.alcoholHeifaScoreMale else user.alcoholHeifaScoreFemale
        val discretionaryScore = if (user.sex == "Male") user.discretionaryHeifaScoreMale else user.discretionaryHeifaScoreFemale
        val totalScore = if (user.sex == "Male") user.heifaTotalScoreMale else user.heifaTotalScoreFemale

        Column(
            modifier = modifier
                .padding(10.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Insights: Food Score",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(30.dp))

            // Then use these scores in your Rows, e.g.
            ScoreRow("Vegetables", vegScore, maxValCategory)
            ScoreRow("Fruits", fruitScore, maxValCategory)
            ScoreRow("Grains & Cereals", grainsScore, maxValCategory)
            ScoreRow("Whole Grains", wholeGrainsScore, maxValCategory)
            ScoreRow("Meat & Alternatives", meatScore, maxValCategory)
            ScoreRow("Dairy", dairyScore, maxValCategory)
            ScoreRow("Water", waterScore, maxValCategory)
            ScoreRow("Unsaturated Fats", unsaturatedFatScore, maxValCategory)
            ScoreRow("Sodium", sodiumScore, maxValCategory)
            ScoreRow("Sugar", sugarScore, maxValCategory)
            ScoreRow("Alcohol", alcoholScore, maxValCategory)
            ScoreRow("Discretionary Foods", discretionaryScore, maxValCategory)

            Spacer(modifier = Modifier.height(3.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(3.dp))

            Text(
                text = "Total Food Quality Score",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                LinearProgressIndicator(
                    progress = totalScore.toFloat() / 100f,
                    modifier = Modifier
                        .height(8.dp)
                        .weight(2f)
                )
                Text(text = "$totalScore/$maxValCategory")
            }

            Spacer(modifier = Modifier.height(1.dp))

            Button(
                onClick = {
                    val shareText = """
                    Food Scores:
                    Vegetables: $vegScore/$maxValCategory
                    Fruits: $fruitScore/$maxValCategory
                    Grains & Cereals: $grainsScore/$maxValCategory
                    Whole Grains: $wholeGrainsScore/$maxValCategory
                    Meat & Alternatives: $meatScore/$maxValCategory
                    Dairy: $dairyScore/$maxValCategory
                    Water: $waterScore/$maxValCategory
                    Unsaturated Fats: $unsaturatedFatScore/$maxValCategory
                    Sodium: $sodiumScore/$maxValCategory
                    Sugar: $sugarScore/$maxValCategory
                    Alcohol: $alcoholScore/$maxValCategory
                    Discretionary Foods: $discretionaryScore/$maxValCategory
                    Total Food Quality Score: $totalScore/100
                """.trimIndent()

                    val shareIntent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, shareText)
                        type = "text/plain"
                    }
                    val chooser = Intent.createChooser(shareIntent, "Share via")
                    context.startActivity(chooser)
                },
                modifier = Modifier.fillMaxWidth(0.8f),
                elevation = ButtonDefaults.elevatedButtonElevation(
                    defaultElevation = 12.dp,
                    pressedElevation = 20.dp
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Share Icon",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Share With Someone")
            }

            Button(onClick = {
                navToNutriCoach()
            } , modifier = Modifier.fillMaxWidth(0.8f) ,  elevation = ButtonDefaults.elevatedButtonElevation(
                defaultElevation = 12.dp,
                pressedElevation = 20.dp
            )) {
                Icon(
                    imageVector = Icons.Default.Rocket,
                    contentDescription = "Save Icon",
                    modifier = Modifier.size(24.dp) // Adjust size if needed
                )
                Spacer(modifier = Modifier.width(8.dp)) // Add space between icon and text
                Text("Improve my diet")
            }

        }
    }
}








// Helper composable to avoid repetition
@Composable
fun ScoreRow(label: String, score: Double, maxVal: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )
        LinearProgressIndicator(
            progress = score.toFloat() / maxVal.toFloat(),
            modifier = Modifier
                .height(8.dp)
                .weight(2f)
        )
        Text(text = "$score/$maxVal")
    }
}




