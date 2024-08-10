package com.example.guardx.ui.theme

import android.util.Patterns
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth


@Composable
fun LoginScreen() {
    val navController = rememberNavController()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val auth = FirebaseAuth.getInstance()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "GuardX Login", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (email.isBlank() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    errorMessage = "Invalid email address"
                    return@Button
                }
                if (password.isBlank()) {
                    errorMessage = "Password cannot be empty"
                    return@Button
                }
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            navController.navigate("home") // Navigate to home screen
                        } else {
                            errorMessage = "Login failed: ${task.exception?.message}"
                        }
                    }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }

        Spacer(modifier = Modifier.height(8.dp))

        //Signup Button
        Button(
            onClick = {
                if (email.isBlank() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    errorMessage = "Invalid email address"
                    return@Button
                }
                if (password.isBlank()) {
                    errorMessage = "Password cannot be empty"
                    return@Button
                }

                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            navController.navigate("home")
                            errorMessage = null
                        } else {
                            errorMessage = "Signup failed: ${task.exception?.message}"
                        }
                    }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Sign Up")
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = {
            // Optionally, handle "Forgot Password" or "Sign Up"
        }) {
            Text("Forgot Password?")
        }
        errorMessage?.let {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = it,
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
