package com.example.assignment1.junyu32025130.screen

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Edit

import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.assignment1.junyu32025130.R
import com.example.assignment1.junyu32025130.viewModel.PatientViewModel


@Composable
fun HomeScreen(patientViewModel: PatientViewModel, modifier: Modifier = Modifier,navToQuestionnaire : () -> Unit ,navToInsights : () -> Unit) {
    val context = LocalContext.current
    val currentUser by patientViewModel.currentPatientData


    LaunchedEffect(Unit) {
        // fetch use data only if the data was not loaded by other screens before
        if (currentUser == null) {
            val currentUserPref = context.getSharedPreferences("currentUser", Context.MODE_PRIVATE)
            val userId = currentUserPref.getString("userId", "")?.toIntOrNull()
            if (userId != null) {
                patientViewModel.loadCurrentPatientData(userId)
            }
        }
    }

    currentUser?.let { user ->
        Column(
            modifier = modifier.padding(start = 10.dp).verticalScroll(rememberScrollState()),
        ) {
            Text(
                text = "Hello,",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
            )

            Text(
                text = "User ${user.userId}",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "You've already filled your Food Intake Questionnaire, but you can change your details here.",
                    modifier = Modifier.fillMaxWidth(0.7f),
                    fontSize = 14.sp,
                )
                Button(onClick = {
                    navToQuestionnaire()
                }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Save Icon",
                        modifier = Modifier.size(24.dp) // Adjust size if needed
                    )
                    Spacer(modifier = Modifier.width(8.dp)) // Add space between icon and text
                    Text("Edit")
                }

            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.healthy_plate),
                    contentDescription = "healthy plate",
                    modifier = Modifier.size(300.dp) // Adjust size as needed
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "My Score",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "See all scores  >",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(end = 5.dp).clickable {
                        // Handle click action here
                        navToInsights()
                    }
                )


            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowUpward,
                        contentDescription = "Up Arrow",
                        tint = Color.Black,
                        modifier = Modifier.size(16.dp)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))


                Text(
                    text = "Your Overall Food Quality Score",
                    fontSize = 14.sp,
                )

                Spacer(modifier = Modifier.weight(1f))

                val score = if (user.sex == "Male") user.heifaTotalScoreMale
                else user.heifaTotalScoreFemale

                val scoreColor = when {
                    score < 40.0 -> Color.Red
                    score in 40.0..69.9 -> Color(0xFFFFA500) // Orange
                    else -> Color.Green
                }

                Text(
                    text = String.format("%.1f", score), // Format to 1 decimal place
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = scoreColor,
                    modifier = Modifier.padding(end = 5.dp)
                )


            }

            Spacer(modifier = Modifier.height(30.dp))
            HorizontalDivider()

            Text(
                text = "What is the Food Quality Score",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Your Food Quality Score provides a snapshot of how well your eating patterns align with established food guidelines, helping you identify both strengths and opportunities for improvement in your diet.\n\n This personalized measurement considers various food groups including vegetables, fruits, whole grains, and proteins to give you practical insights for making healthier food choices.",
                fontSize = 14.sp,
            )

        }


//        Button(onClick = {
//            val currentUserPref = context.getSharedPreferences("currentUser", Context.MODE_PRIVATE)
//            val userId = currentUserPref.getString("userId","")
//            val sharedPreferences = context.getSharedPreferences("UserPreferences_$userId", Context.MODE_PRIVATE)
//            sharedPreferences.edit().clear().apply()
//        }) {
//
//        }
    }
}
