package com.example.guardx

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.guardx.ui.theme.HomeScreen
import com.example.guardx.ui.theme.LoginScreen

@Composable
fun GuardXApp() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "login") {
        composable("login") { LoginScreen() }
        composable("home") { HomeScreen() }
//        composable("first_aid") { FirstAidScreen() }
//        composable("be_volunteer") { BeVolunteerScreen() }
        // Add more composable destinations for other pages
    }
}
