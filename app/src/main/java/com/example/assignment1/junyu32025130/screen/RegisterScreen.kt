package com.example.assignment1.junyu32025130.screen

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf

import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.assignment1.junyu32025130.viewModel.PatientViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(patientViewModel: PatientViewModel, onRegisterSucess : () -> Unit , navToLogin: () -> Unit) {
    val context = LocalContext.current
    val users by patientViewModel.allUsers.collectAsState(initial = emptyList())
    var selectedUserId by rememberSaveable  { mutableStateOf("") }
    var phoneNumer by rememberSaveable  { mutableStateOf("") }
    var password by rememberSaveable  { mutableStateOf("") }
    val isPasswordValid = password.length >= 6
    var confirmPassword by rememberSaveable  { mutableStateOf("") }
    var expanded by rememberSaveable  { mutableStateOf(false) }

    val isAllFieldsFilled = selectedUserId.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty() && phoneNumer.isNotEmpty()
    val isPhoneCorrect = selectedUserId.isNotEmpty() &&  users.find{ it.userId == selectedUserId.toInt()}?.phoneNumber == phoneNumer
    val passwordsMatch = confirmPassword == password
    val isReadyToRegister = isAllFieldsFilled && isPhoneCorrect && passwordsMatch && isPasswordValid
    Card(
    modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(),
    shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    colors = CardDefaults.cardColors(Color.White)
    ){
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp).verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ){

            Text(
                text = "Register",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.Underline
                )
            )
            Spacer(modifier = Modifier.height(50.dp))


            // Dropdown for User ID
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = it }
            ) {
                OutlinedTextField(
                    value = selectedUserId,
                    onValueChange = {},
                    label = { Text("My ID (Provided by your Clinician)") },
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },

                    ) {

                    users.filter { it.password == null }.forEachIndexed { index ,user ->
                        DropdownMenuItem(
                            onClick = {
                                selectedUserId= user.userId.toString()
                                expanded = false
                            },
                            text = { Text(user.userId.toString()) }
                        )
                    }

                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = phoneNumer,
                onValueChange = { phoneNumer = it},
                label = { Text("Enter Phone Number") },
                isError =  phoneNumer.isNotEmpty() && !isPhoneCorrect,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number ,imeAction = ImeAction.Done),
                modifier = Modifier
                    .fillMaxWidth(),
                supportingText = {
                    if ( phoneNumer.isNotEmpty() && !isPhoneCorrect) {
                        Text(
                            text = "Invalid phone number for user $selectedUserId",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.labelSmall,
                        )
                    }


                }

            )


            Spacer(modifier = Modifier.height(16.dp))


            OutlinedTextField(
                value = password,
                onValueChange = { password = it},
                label = { Text("Enter Password") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                isError =  password.isNotEmpty() && !isPasswordValid,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth(),
                supportingText = {
                    if (!isPasswordValid && password.isNotEmpty()) {
                        Text(
                            text = "Password must be at least 6 characters",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.labelSmall,
                        )
                    }


                }


            )

            Spacer(modifier = Modifier.height(16.dp))



            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Re-enter Password") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                visualTransformation = PasswordVisualTransformation(),
                isError = confirmPassword.isNotEmpty() && !passwordsMatch, // only show error if user typed something
                modifier = Modifier.fillMaxWidth(),
                supportingText = {
                    if (confirmPassword.isNotEmpty() && !passwordsMatch) {
                        Text(
                            text = "Passwords do not match",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            )


            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "This app is only for pre-registered users.Please enter your ID,phone number and password to claim your account",
                fontSize = 14.sp,
            )

            Spacer(modifier = Modifier.height(10.dp))


            Button(
                onClick = {

                    // All checks passed
                    patientViewModel.updatePassword(selectedUserId.toInt(), password)
                    Toast.makeText(context, "Register successful", Toast.LENGTH_LONG).show()
                    onRegisterSucess()


                },
                enabled = isReadyToRegister,
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Text("Register")
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = {
                    navToLogin()
                },
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Text("Continue")
            }

            HorizontalDivider()
        }
    }
}