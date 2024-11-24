package com.example.application_mobile

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun F_Pass() {
    val context = LocalContext.current

    val Email = remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.forget_p),
            contentDescription = "Forget Password Image",
            modifier = Modifier.size(220.dp)
        )
        Spacer(modifier = Modifier.height(50.dp))

        Text(text = "Welcome Back !!", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "Please enter your email to send the password and username!", textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(20.dp))

        // Champ pour l'email
        OutlinedTextField(
            value = Email.value,
            onValueChange = { Email.value = it },
            label = { Text(text = "Email") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF5DD2A3), // Vert pistache
                unfocusedBorderColor = Color.Gray,
                cursorColor = Color(0xFF5DD2A3), // Vert pistache
                focusedLabelColor = Color(0xFF5DD2A3) // Vert pistache
            ),
            shape = RoundedCornerShape(8.dp) // Ajout d'un coin arrondi
        )
        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            val intent = Intent(context, FP_C::class.java)
            context.startActivity(intent)
        },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF5DD2A3), // Vert pistache
                contentColor = Color.White
            ),
        ) {
            Text(text = "Send ")
        }
    }
}
class forget_pass : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            F_Pass()
        }
    }
}