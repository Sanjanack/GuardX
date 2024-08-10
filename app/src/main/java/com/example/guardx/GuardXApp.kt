package com.example.guardx

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.guardx.ui.theme.AuthScreen
import com.example.guardx.ui.theme.DisasterPreparednessScreen
import com.example.guardx.ui.theme.FirstAidScreen
import com.example.guardx.ui.theme.HomeScreen
import com.example.guardx.ui.theme.NewsScreen
import com.example.guardx.ui.theme.VolunteerScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun GuardXApp() {
    val navController = rememberNavController()
    fun handleLogout() {
        // Sign out from Firebase
        FirebaseAuth.getInstance().signOut()
        // Navigate to the login screen
        navController.navigate("login") {
            // Clear back stack
            popUpTo("home") { inclusive = true }
        }
    }
    NavHost(navController, startDestination = "login") {
        composable("login") { AuthScreen(navController) }
        composable("home") { HomeScreen(navController, onLogout = { handleLogout() }) }
        composable("volunteer") { VolunteerScreen(navController) }
        composable("news") { NewsScreen(navController) }
        composable("firstAid") { FirstAidScreen(navController) }
        composable("alerts") { DisasterPreparednessScreen(navController) }


        // Add more composable destinations for other pages
    }
}
