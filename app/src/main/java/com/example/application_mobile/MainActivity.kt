package com.example.application_mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Retrieve saved login information
        val (savedUsername, savedPassword) = getLoginInfo(this)

        setContent {
            if (!savedUsername.isNullOrEmpty() && !savedPassword.isNullOrEmpty()) {
                // Show the home screen if the user is logged in
                Acceuil()
            } else {
                // Otherwise, show the login screen
                Login()
            }
        }
    }
}
