package com.example.assignment1.junyu32025130.screen

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.example.assignment1.junyu32025130.R
import com.example.assignment1.junyu32025130.component.LabelledCheckbox
import com.example.assignment1.junyu32025130.component.TimePickerFun
import com.example.assignment1.junyu32025130.entity.FoodIntake
import com.example.assignment1.junyu32025130.type.HealthPersona

import com.example.assignment1.junyu32025130.util.validateTimes
import com.example.assignment1.junyu32025130.viewModel.FoodIntakeViewModel
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionnaireScreen(foodIntakeViewModel : FoodIntakeViewModel,modifier :Modifier, onDone : () -> Unit ) {
    val context = LocalContext.current
    val foodIntakeData by foodIntakeViewModel.currentUserFoodIntake.collectAsState(initial = null)
    var userId by rememberSaveable { mutableStateOf<Int?>(null) }

    // State variables to store the data, initialized once data is available
    var isFruitsChecked by rememberSaveable { mutableStateOf(false) }
    var isVegetablesChecked by rememberSaveable { mutableStateOf(false) }
    var isGrainsChecked by rememberSaveable { mutableStateOf(false) }
    var isRedMeatChecked by rememberSaveable { mutableStateOf(false) }
    var isFishChecked by rememberSaveable { mutableStateOf(false) }
    var isNutsChecked by rememberSaveable { mutableStateOf(false) }
    var isSeafoodChecked by rememberSaveable { mutableStateOf(false) }
    var isEggsChecked by rememberSaveable { mutableStateOf(false) }
    var isPoultryChecked by rememberSaveable { mutableStateOf(false) }
    var selectedPersona by rememberSaveable { mutableStateOf("") }

    val mTimeEat = rememberSaveable { mutableStateOf("00:00") }
    val mTimeSleep = rememberSaveable { mutableStateOf("00:00") }
    val mTimeWake = rememberSaveable { mutableStateOf("00:00") }
    val hasLoaded = rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (!hasLoaded.value) {
            val currentUserPref = context.getSharedPreferences("currentUser", Context.MODE_PRIVATE)
            userId = currentUserPref.getString("userId", "")?.toIntOrNull()
            Log.d("userid", "QuestionnaireScreen: $userId")

            if (userId != null) {
                foodIntakeViewModel.loadFoodIntake(userId!!)
            }

            // Wait for ViewModel to load data
            snapshotFlow { foodIntakeData }
                .filterNotNull()
                .first() // suspend until data is available

            // Once data is ready, populate the state
            foodIntakeData?.let { data ->
                isFruitsChecked = data.isFruitsChecked
                isVegetablesChecked = data.isVegetablesChecked
                isGrainsChecked = data.isGrainsChecked
                isRedMeatChecked = data.isRedMeatChecked
                isFishChecked = data.isFishChecked
                isNutsChecked = data.isNutsChecked
                isSeafoodChecked = data.isSeafoodChecked
                isEggsChecked = data.isEggsChecked
                isPoultryChecked = data.isPoultryChecked
                selectedPersona = data.selectedPersona

                mTimeEat.value = data.mTimeEat
                mTimeSleep.value = data.mTimeSleep
                mTimeWake.value = data.mTimeWake
            }

            hasLoaded.value = true
        }
    }

    Column(
        modifier = modifier.verticalScroll(rememberScrollState()), // Enables scrolling,
        horizontalAlignment = Alignment.CenterHorizontally,


        ) {



        var expanded by rememberSaveable { mutableStateOf(false) }

        val personaList = listOf(
            HealthPersona("Health Devotee", "I’m passionate about healthy eating & health plays a big part in my life. I use social media to follow active lifestyle personalities or get new recipes/exercise ideas. I may even buy superfoods or follow a particular type of diet. I like to think I am super healthy.", R.drawable.health_devotee),
            HealthPersona("Mindful Eater", "I’m health-conscious and being healthy and eating healthy is important to me. Although health means different things to different people, I make conscious lifestyle decisions about eating based on what I believe healthy means. I look for new recipes and healthy eating information on social media.", R.drawable.mindful_eater),
            HealthPersona("Wellness Striver", "I aspire to be healthy (but struggle sometimes). Healthy eating is hard work! I’ve tried to improve my diet, but always find things that make it difficult to stick with the changes. Sometimes I notice recipe ideas or healthy eating hacks, and if it seems easy enough, I’ll give it a go.", R.drawable.wellness_striver),
            HealthPersona("Balance Seeker", "I try and live a balanced lifestyle, and I think that all foods are okay in moderation. I shouldn’t have to feel guilty about eating a piece of cake now and again. I get all sorts of inspiration from social media like finding out about new restaurants, fun recipes and sometimes healthy eating tips.", R.drawable.balance_seeker),
            HealthPersona("Health Procrastinator", "I’m contemplating healthy eating but it’s not a priority for me right now. I know the basics about what it means to be healthy, but it doesn’t seem relevant to me right now. I have taken a few steps to be healthier but I am not motivated to make it a high priority because I have too many other things going on in my life.", R.drawable.health_procrastinator),
            HealthPersona("Food Carefree", "I’m not bothered about healthy eating. I don’t really see the point and I don’t think about it. I don’t really notice healthy eating tips or recipes and I don’t care what I eat.", R.drawable.food_carefree)
        )

        var selectedPersonaDialog by rememberSaveable { mutableStateOf<HealthPersona?>(null) }
        var showPersonaDescriptionDialog by rememberSaveable {
            mutableStateOf(false)
        }
        val mTimePickerEatDialog = TimePickerFun(mTimeEat)
        val mTimePickerSleepDialog = TimePickerFun(mTimeSleep)
        val mTimePickerWakeDialog = TimePickerFun(mTimeWake)

        Text(text = "Tick all the food categories you can eat",modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(), fontWeight = FontWeight.Medium, textAlign = TextAlign.Start)


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column {
                LabelledCheckbox(label = "Fruits", checked = isFruitsChecked, onCheckedChange = { isFruitsChecked = it })
                LabelledCheckbox(label = "Red Meat", checked = isRedMeatChecked, onCheckedChange = { isRedMeatChecked = it })
                LabelledCheckbox(label = "Fish", checked = isFishChecked, onCheckedChange = { isFishChecked = it })
            }
            Column {
                LabelledCheckbox(label = "Vegetables", checked = isVegetablesChecked, onCheckedChange = { isVegetablesChecked = it })
                LabelledCheckbox(label = "Seafood", checked = isSeafoodChecked, onCheckedChange = { isSeafoodChecked = it })
                LabelledCheckbox(label = "Eggs", checked = isEggsChecked, onCheckedChange = { isEggsChecked = it })
            }
            Column {
                LabelledCheckbox(label = "Grains", checked = isGrainsChecked, onCheckedChange = { isGrainsChecked = it })
                LabelledCheckbox(label = "Poultry", checked = isPoultryChecked, onCheckedChange = { isPoultryChecked = it })
                LabelledCheckbox(label = "Nuts/Seeds", checked = isNutsChecked, onCheckedChange = { isNutsChecked = it })
            }
        }

        Spacer(modifier = Modifier.height(5.dp))
        HorizontalDivider()

        Text(text = "Your Persona",modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(), fontWeight = FontWeight.Medium ,textAlign = TextAlign.Start)

        Text(text = "People can be broadly classified into 6 different types based on their eating preferences.Click on each button below to find out the different types, and select the type that best fits you",modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp), textAlign = TextAlign.Start ,fontSize = 14.sp,)

        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ){
            personaList.take(3).forEachIndexed { index, persona ->
                Button(
                    onClick = {selectedPersonaDialog = persona },
                    modifier = Modifier
                        .weight(1f)
                        .padding(2.dp)
                ) {
                    Text(text = persona.name,textAlign = TextAlign.Center,fontSize = 12.sp)
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
        ){
            personaList.drop(3).take(3).forEachIndexed { index, persona ->
                Button(
                    onClick = { selectedPersonaDialog = persona },
                    modifier = Modifier
                        .weight(1f)
                        .padding(2.dp)
                ) {
                    Text(text = persona.name,textAlign = TextAlign.Center,fontSize = 12.sp)
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        HorizontalDivider()

        Text(text = "Which persona best fits you",modifier = Modifier
            .padding(start = 10.dp)
            .fillMaxWidth(), fontWeight = FontWeight.Medium ,textAlign = TextAlign.Start)


        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it },
            modifier = Modifier.padding(10.dp)
        ) {
            OutlinedTextField(
                value = selectedPersona,
                onValueChange = {},
                label = { Text("Select Persona") },
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },

                ) {

                personaList.forEachIndexed { index ,persona ->
                    DropdownMenuItem(
                        onClick = {
                            selectedPersona= persona.name
                            expanded = false
                        },
                        text = { Text(persona.name) }
                    )
                }

            }
        }


        selectedPersonaDialog?.let { persona ->
            PersonaDialog(persona = persona, onDismiss = { selectedPersonaDialog = null })
        }

        Spacer(modifier = Modifier.height(10.dp))


        Text(text = "Timings",modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(), fontWeight = FontWeight.Medium ,textAlign = TextAlign.Start)

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(text = "What time of the day approx. do you normally eat your biggest meal",modifier = Modifier
                .padding(10.dp)
                .weight(2f),textAlign = TextAlign.Start,fontSize = 14.sp)


            OutlinedButton(
                onClick = {   mTimePickerEatDialog.show() },
                modifier = Modifier.padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Timer, // or any other icon
                    contentDescription = "Timer Icon",
                    tint = Color.Gray
                )
                Spacer(modifier = Modifier.width(8.dp)) // space between icon and text
                Text(mTimeEat.value)
            }



        }

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(text = "What time of the day approx. do you go to sleep at night",modifier = Modifier
                .padding(10.dp)
                .weight(2f),textAlign = TextAlign.Start,fontSize = 14.sp)

            OutlinedButton(
                onClick = {  mTimePickerSleepDialog.show()},
                modifier = Modifier.padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Timer, // or any other icon
                    contentDescription = "Timer Icon",
                    tint = Color.Gray
                )
                Spacer(modifier = Modifier.width(8.dp)) // space between icon and text
                Text(mTimeSleep.value)
            }


        }

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(text = "What time of the day approx. do you wake up in the morning",modifier = Modifier
                .padding(10.dp)
                .weight(2f),textAlign = TextAlign.Start,fontSize = 14.sp)

            OutlinedButton(
                onClick = {  mTimePickerWakeDialog.show()},
                modifier = Modifier.padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Timer, // or any other icon
                    contentDescription = "Timer Icon",
                    tint = Color.Gray
                )
                Spacer(modifier = Modifier.width(8.dp)) // space between icon and text
                Text(mTimeWake.value)
            }

        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            // Validate times before saving
            if (validateTimes(mTimeEat.value, mTimeSleep.value, mTimeWake.value)) {
                val newFoodIntake = FoodIntake(
                    userId!!,
                    isFruitsChecked,
                    isVegetablesChecked,
                    isGrainsChecked,
                    isRedMeatChecked,
                    isFishChecked,
                    isNutsChecked,
                    isSeafoodChecked,
                    isPoultryChecked,
                    isEggsChecked,
                    selectedPersona,
                    mTimeEat.value,
                    mTimeSleep.value,
                    mTimeWake.value
                )
                foodIntakeViewModel.upsertFoodIntake(newFoodIntake)


                Toast.makeText(context,"Changes Saved",Toast.LENGTH_LONG).show()
                onDone()

            } else {
                // Show an error message or dialog that the times are invalid
                Toast.makeText(context,"Eat time cannot be between sleep time and wake time", Toast.LENGTH_LONG).show()
            }


        } , modifier = Modifier.fillMaxWidth(0.8f),  elevation = ButtonDefaults.elevatedButtonElevation(
            defaultElevation = 12.dp,
            pressedElevation = 20.dp
        )) {
            Icon(
                imageVector = Icons.Default.Save,
                contentDescription = "Save Icon",
                modifier = Modifier.size(24.dp) // Adjust size if needed
            )
            Spacer(modifier = Modifier.width(8.dp)) // Add space between icon and text
            Text("Save")
        }

        Spacer(modifier = Modifier.height(20.dp))

    }



}



@Composable
fun PersonaDialog(persona: HealthPersona, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("OK")
            }
        },
        title = { Text(text = persona.name) },
        text = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = persona.imageRes),
                    contentDescription = persona.name,
                    modifier = Modifier.size(120.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = persona.description)
            }
        },
        shape = RoundedCornerShape(12.dp),
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    )
}