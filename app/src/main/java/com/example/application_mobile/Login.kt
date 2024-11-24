package com.example.application_mobile

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login() {
    val context = LocalContext.current
    val userName = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    // State for showing the alert
    var showAlert by remember { mutableStateOf(false) }
    var alertMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(70.dp))

        Image(
            painter = painterResource(id = R.drawable.login_img),
            contentDescription = "Login Image",
            modifier = Modifier.size(220.dp)
        )

        Text(text = "Welcome Back !!", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "Please enter your username and password !!")
        Spacer(modifier = Modifier.height(20.dp))

        // Username field
        OutlinedTextField(
            value = userName.value,
            onValueChange = { userName.value = it },
            label = { Text(text = "Username") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Blue,
                unfocusedBorderColor = Color.Gray,
                cursorColor = Color.Blue,
                focusedLabelColor = Color.Blue
            ),
            shape = RoundedCornerShape(8.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Password field
        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text(text = "Password") },
            visualTransformation = PasswordVisualTransformation(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Blue,
                unfocusedBorderColor = Color.Gray,
                cursorColor = Color.Blue,
                focusedLabelColor = Color.Blue
            ),
            shape = RoundedCornerShape(8.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                Log.d("Login", "Attempting login for user: ${userName.value}")

                // Check if the username and password are correct
                if (userName.value == "Achref0" && password.value == "123456") {
                    Log.d("Login", "Login successful for user: ${userName.value}")

                    // Save login info
                    saveLoginInfo(context, userName.value, password.value)

                    // Start the next activity
                    val intent = Intent(context, AcceuilP::class.java) // Ensure this activity exists
                    context.startActivity(intent)

                    // Finish the login activity to prevent returning to it
                    (context as? Activity)?.finish()
                } else {
                    Log.d("Login", "Login failed for user: ${userName.value}")
                    showAlert = true
                    alertMessage = "Invalid username or password"
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Blue,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = "Login")
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Forgot Password?",
            modifier = Modifier.clickable {
                val intent = Intent(context, forget_pass::class.java)
                context.startActivity(intent)
            },
            color = Color.Blue
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Button to navigate to Register screen
        Button(
            onClick = {
                val intent = Intent(context, Register_C::class.java) // Ensure Register is imported
                context.startActivity(intent)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Blue,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = "Sign In")
        }

        // Alert dialog for showing error messages
        if (showAlert) {
            AlertDialog(
                onDismissRequest = { showAlert = false },
                title = { Text("Error") },
                text = { Text(alertMessage) },
                confirmButton = {
                    Button(onClick = { showAlert = false }) {
                        Text("OK")
                    }
                }
            )
        }
    }
}

private fun saveLoginInfo(context: Context, username: String, password: String) {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("login_prefs", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("username", username)
    editor.putString("password", password)
    editor.apply()
    Log.d("Login", "Saved username: $username and password: $password")
}

fun getLoginInfo(context: Context): Pair<String?, String?> {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("login_prefs", Context.MODE_PRIVATE)
    val username = sharedPreferences.getString("username", null)
    val password = sharedPreferences.getString("password", null)
    Log.d("Login", "Retrieved username: $username and password: $password")
    return Pair(username, password)
}


class LoginP : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Login()
        }
    }
}
