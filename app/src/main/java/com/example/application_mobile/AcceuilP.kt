package com.example.application_mobile

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.application_mobile.data.Product

@Composable
fun Acceuil() {

    val context = LocalContext.current
    val (savedUsername, _) = getLoginInfo(context)

    val products = listOf(
        Product("Mercedes", " $22,454 - $28,092", "Description of product 1", R.drawable.car1),
        Product("Range Rover", "$22,454 - $28,092", "Description of product 2", R.drawable.car2),
        Product("Porsh ", "$22,454 - $28,092", "Description of product 3", R.drawable.car3),
        Product("Porsh ", "$22,454 - $28,092", "Description of product 3", R.drawable.car3),
        Product("Porsh ", "$22,454 - $28,092", "Description of product 3", R.drawable.car3)

    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), // Add padding around the entire content
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Static content like welcome message and image
        item {
            Spacer(modifier = Modifier.height(70.dp))

            Text(text = "Welcome Back  !!", fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "This is the home page you Welcome Mr ${savedUsername ?: "User"} !!! ")
            Spacer(modifier = Modifier.height(40.dp))
        }

        // List of products
        items(products) { product ->
            ProductItem(product)
        }

        // Static content like the logout button at the bottom
        item {
            Spacer(modifier = Modifier.height(40.dp))
            // BUTTON DB
            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = {
                    // Clear login information
                    val sharedPreferences: SharedPreferences = context.getSharedPreferences("login_prefs", Context.MODE_PRIVATE)
                    sharedPreferences.edit().clear().apply()

                    // Navigate back to the login page
                    val intent = Intent(context, LoginP::class.java)
                    context.startActivity(intent)
                    (context as Activity).finish() // Finish current activity
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFECA52B), // Gold color
                    contentColor = Color.White,
                )
            ) {
                Text(text = "Logout", style = TextStyle(fontWeight = FontWeight.Bold))
            }
        }
    }
}
@Composable
fun ProductItem(product: Product) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.LightGray // Set the background color to light gray
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = product.imageUrl),
                contentDescription = product.name,
                modifier = Modifier.size(240.dp)
            )
            Spacer(modifier = Modifier.height(8.dp)) // Add space between image and text
            Text(text = product.name, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(text = product.price, fontSize = 16.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(8.dp))

            // "Consulter" Button to navigate to GestionT
            Button(
                onClick = {
                    // Navigate to GestionT
                    val intent = Intent(context, GestionT::class.java)
                    intent.putExtra("productName", product.name) // Pass product name as extra data
                    context.startActivity(intent)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6200EA), // Purple color
                    contentColor = Color.White,
                )
            ) {
                Text(text = "Consulter", style = TextStyle(fontWeight = FontWeight.Bold))
            }
        }
    }
}


fun showProductDetails(product: Product) {
    // Handle showing product details (you could use a dialog, new screen, etc.)
    // For simplicity, this is just a placeholder function.
    println("Showing details for: ${product.name}")
}

class AcceuilP : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val (savedUsername, savedPassword) = getLoginInfo(this)

        if (!savedUsername.isNullOrEmpty() && !savedPassword.isNullOrEmpty()) {
            setContent {
                Acceuil()
            }
        } else {
            setContent {
                Login()
            }
        }}}