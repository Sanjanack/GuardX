package com.example.guardx

import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.guardx.ui.theme.AuthScreen
import com.example.guardx.ui.theme.DisasterPreparednessScreen
import com.example.guardx.ui.theme.FirstAidScreen
import com.example.guardx.ui.theme.HomeScreen
import com.example.guardx.ui.theme.NewsScreen
import com.example.guardx.ui.theme.VolunteerScreen
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.firebase.auth.FirebaseAuth
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.runtime.LaunchedEffect
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


@Composable
fun GuardXApp(fusedLocationClient: FusedLocationProviderClient, context: Context) {
    var location by remember { mutableStateOf("Location not available") }
    var permissionGranted by remember { mutableStateOf(false) }


    // Register the permission request launcher
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        permissionGranted = isGranted
        if (isGranted) {
            fetchLocation(fusedLocationClient) { loc ->
                location = loc
            }
        } else {
            location = "Permission denied"
        }
    }
    LaunchedEffect(Unit) {
        when {
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED -> {
                permissionGranted = true
                fetchLocation(fusedLocationClient) { loc ->
                    location = loc
                }
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = location)

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }) {
            Text(text = "Get Location")
        }

        // Add your other navigation code and UI components here
    }


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

private fun fetchLocation(
    fusedLocationClient: FusedLocationProviderClient,
    onLocationFetched: (String) -> Unit
) {
    try {
        fusedLocationClient.lastLocation.addOnSuccessListener { locationResult ->
            locationResult?.let {
                onLocationFetched("Latitude: ${it.latitude}, Longitude: ${it.longitude}")
            } ?: run {
                onLocationFetched("Unable to fetch location")
            }
        }
    } catch (e: SecurityException) {
        onLocationFetched("Location permission not granted")
    }
}
