import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp

import com.example.assignment1.junyu32025130.R

@Composable
fun WelcomeScreen(modifier: Modifier = Modifier, onNext :() -> Unit) {

    val context = LocalContext.current
    val annotatedString = buildAnnotatedString {
        val linkText = "Visit Monash Nutrition Clinic"
        val url = "https://www.monash.edu/medicine/scs/nutrition/clinics/nutrition"

        append(linkText)
        addStyle(
            style = SpanStyle(
                color = Color.Blue,
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight.Bold
            ),
            start = 0,
            end = linkText.length
        )
        addStringAnnotation(
            tag = "URL",
            annotation = url,
            start = 0,
            end = linkText.length
        )
    }
    Surface(
        modifier = modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp).verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Add image at the top
            Image(
                painter = painterResource(id = R.drawable.nutritracklogo),
                contentDescription = "app Logo",
                modifier = Modifier.size(200.dp) // Adjust size as needed
            )
            // Add space between logo and username
            Spacer(modifier = Modifier.height(24.dp))

            Text("NutriTrack is a mobile application designed to help users track their food intake and gain insights into their dietary habits. The information provided by NutriTrack is for informational purposes only and should not be considered medical or nutritional advice.")

            // Add space between username and password
            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    onNext()
                },
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Text("Login")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = annotatedString,
                modifier = Modifier.clickable {
                    val url = annotatedString.getStringAnnotations("URL", 0, annotatedString.length)
                        .firstOrNull()?.item
                    url?.let {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                        context.startActivity(intent)
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Student Name + ID
            Text(text = "Tan Jun Yu (32025130)")
        }
    }
}