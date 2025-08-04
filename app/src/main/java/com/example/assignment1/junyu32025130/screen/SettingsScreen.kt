package com.example.assignment1.junyu32025130.screen

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable

import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.Male
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.sp
import com.example.assignment1.junyu32025130.api.service.GenAiUiState
import com.example.assignment1.junyu32025130.entity.Patient
import com.example.assignment1.junyu32025130.util.calculateAverageScore
import com.example.assignment1.junyu32025130.viewModel.GenAiViewModel
import com.example.assignment1.junyu32025130.viewModel.PatientViewModel

@Composable
fun SettingsScreen(
    patientViewModel: PatientViewModel,
    genAiViewModel: GenAiViewModel,
    onLogOut : () -> Unit
) {
    val context = LocalContext.current
    val currentUser by patientViewModel.currentPatientData

    var showClinicianLoginView by rememberSaveable {
        mutableStateOf(false)
    }

    var showAdminView by rememberSaveable {
        mutableStateOf(false)
    }

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

    if(showAdminView){
        AdminView(patientViewModel = patientViewModel,genAiViewModel = genAiViewModel){
            showClinicianLoginView = false
            showAdminView = false
        }
    } else if (showClinicianLoginView){
        ClinicianLoginView(onAdminLoginSuccessful =   {
            showClinicianLoginView = false
            showAdminView = true
            Toast.makeText(context,"Login successful.",Toast.LENGTH_LONG).show()
        } , backToSettings = {
            showClinicianLoginView = false
            showAdminView = false
        }) {
            Toast.makeText(context,"Wrong admin key. Please try again.",Toast.LENGTH_LONG).show()
        }
    } else {
        SettingsView(currentUser, onLogOut = {
            val prefs = context.getSharedPreferences("currentUser", Context.MODE_PRIVATE)
            prefs.edit().clear().apply()
            onLogOut()
        }){
            showClinicianLoginView = true
        }
    }



}

@Composable
fun SettingsView(currentUser : Patient?, onLogOut: () -> Unit, toClinicianLogin : () -> Unit){


    val userIdState = currentUser?.userId?.toString() ?: "N/A"
    val phoneState = currentUser?.phoneNumber ?: "N/A"
    val genderState = currentUser?.sex ?: "N/A"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Settings",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "ACCOUNT",
            style = MaterialTheme.typography.labelMedium.copy(color = Color.Gray),
        )

        Spacer(modifier = Modifier.height(8.dp))

        AccountItem(icon = Icons.Default.Person, info = userIdState)
        AccountItem(icon = Icons.Default.Phone, info = phoneState)
        val genderIcon = when (genderState) {
            "Male" -> Icons.Default.Male
            "Female" -> Icons.Default.Female
            else -> Icons.Default.Person
        }

        AccountItem(icon = genderIcon, info =genderState)

        Divider(modifier = Modifier.padding(vertical = 16.dp))

        Text(
            text = "OTHER SETTINGS",
            style = MaterialTheme.typography.labelMedium.copy(color = Color.Gray),
        )

        Spacer(modifier = Modifier.height(8.dp))

        SettingsOption(
            icon = Icons.Outlined.ExitToApp,
            label = "Logout",
            onClick = {
                onLogOut()
            }
        )

        SettingsOption(
            icon = Icons.Default.Person,
            label = "Clinician Login",
            onClick = {
                toClinicianLogin()
            }
        )
    }
}

@Composable
fun AccountItem(icon: ImageVector, info: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(10.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = info, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun SettingsOption(icon: ImageVector, label: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = Color(0xFF6B4EFF)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = label, style = MaterialTheme.typography.bodyLarge)
        }
        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = null,
            tint = Color.Gray
        )
    }
}

@Composable
fun ClinicianLoginView(onAdminLoginSuccessful : () -> Unit , backToSettings: () -> Unit, onAdminLoginFail : () -> Unit){

    var adminKey by rememberSaveable {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Clinician Login",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(50.dp))
        OutlinedTextField(
            value = adminKey,
            onValueChange = { adminKey = it},
            label = { Text("Enter Password") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()

        )

        Spacer(modifier = Modifier.height(60.dp))

        Button(
            onClick = {
                if(adminKey.trim() == "dollar-entry-apples"){
                    onAdminLoginSuccessful()
                }else {
                    onAdminLoginFail()
                }

            },
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Login,
                    contentDescription = null,
                    tint = Color.White // match button text color
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Login",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Button(onClick = {backToSettings()},modifier = Modifier.fillMaxWidth(0.8f)) {
            Text("Back to settings")
        }


    }
}

@Composable
fun AdminView(patientViewModel: PatientViewModel,genAiViewModel: GenAiViewModel , backToSettings : () -> Unit){
    val context = LocalContext.current
    val allUsers = patientViewModel.allUsers.collectAsState(initial = emptyList())
    val genAiState by genAiViewModel.uiDataPatternState.collectAsState()
    var result by rememberSaveable { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text(
            text = "Clinician Dashboard",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = calculateAverageScore(isMale = true, users = allUsers.value).toString(),
            onValueChange = {},
            label = { Text("Average HEIFA (Male)") },
            readOnly = true,
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = calculateAverageScore(isMale = false, users = allUsers.value).toString(),
            onValueChange = {},
            label = { Text("Average HEIFA (Female)") },
            readOnly = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        HorizontalDivider()

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                genAiViewModel.sendDataPatternPrompt(users = allUsers.value)
                      },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Icon(Icons.Default.Search, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Find Data Pattern")
        }

        Spacer(modifier = Modifier.height(16.dp))


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

                Toast.makeText(context,"Failed to fetch insights. ${result}",Toast.LENGTH_LONG).show()

            }
            // Handle success state
            else if (genAiState is GenAiUiState.Success) {
                // Use default text color for success
                textColor = MaterialTheme.colorScheme.onSurface
                // Update result with the generated content
                result = (genAiState as GenAiUiState.Success).outputText

                val insights = result.split("|").map { it.trim() }

                if(insights.isNotEmpty()){
                        insights.forEach{insight ->

                            Card(
                                shape = RoundedCornerShape(12.dp),
                                elevation = CardDefaults.cardElevation(6.dp),
                                modifier = Modifier
                                    .padding(8.dp)
                                    .fillMaxWidth()
                            ) {

                                Text(insight, fontSize = 14.sp , modifier = Modifier.padding(10.dp))
                            }

                        }

                    }


            }
            


        }
        Spacer(modifier = Modifier.weight(1f))


        Button(
            onClick = { backToSettings() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Text("Done")
        }
    }
}


