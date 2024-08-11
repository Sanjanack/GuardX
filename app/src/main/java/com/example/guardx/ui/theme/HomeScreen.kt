package com.example.guardx.ui.theme

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.guardx.R
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.runtime.collectAsState
import com.google.android.gms.location.FusedLocationProviderClient


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavHostController, onLogout: () -> Unit, newsViewModel: NewsViewModel = viewModel()
) {
    var expanded by remember { mutableStateOf(false) }
    var menuExpanded by remember { mutableStateOf(false) }
    var nestedMenuExpanded by remember { mutableStateOf(false) }
    var profileExpanded by remember { mutableStateOf(false) }
    val quote = "Empowering You to Stand Strong in Any Storm!"
    val fontFamily = FontFamily(Font(R.font.nyala))
    val headlines by newsViewModel.headlines.collectAsState()
    Scaffold(
        topBar = {
            CustomTopAppBar(
                onMenuClick = { menuExpanded = true },
                onProfileClick = { profileExpanded = true }
            )
        },content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Welcome to GuardX",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.padding(8.dp))
                RunningHeadlines(headlines = headlines)
                Spacer(modifier = Modifier.padding(8.dp))
                Image(painter = painterResource(id = R.drawable.image),
                    contentDescription = "Description of the image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop)
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text = quote,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    lineHeight = 30.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(bottom = 16.dp)

                )
            }
        }
    )
                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false }
                    ) {

                        DropdownMenuItem(text = { Text("Be a Volunteer") },onClick = {
                            menuExpanded = false
                            navController.navigate("volunteer")
                        })

                        DropdownMenuItem(text = { Text("News & Updates") },onClick = {
                            menuExpanded = false
                            navController.navigate("news")
                        })
                        DropdownMenuItem(text ={Text("Educate yourself")}, onClick = { nestedMenuExpanded = !nestedMenuExpanded })
                        DropdownMenu(
                            expanded = nestedMenuExpanded,
                            onDismissRequest = { nestedMenuExpanded = false }
                        ) {
                            //Nested dropdown
                            DropdownMenuItem(text = { Text("Learn First Aid") }, onClick = {
                                menuExpanded = false
                                navController.navigate("firstAid")
                            })
                            DropdownMenuItem(text = { Text("Disaster Preparedness") },onClick = {
                                menuExpanded = false
                                navController.navigate("alerts")
                            })
                        }
                    }

                    // Profile Icon with Dropdown Menu
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(text = { Text("Logout") },
                            onClick = {
                            expanded = false
                            onLogout() // Call the logout action
                        })
                    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(onMenuClick: () -> Unit, onProfileClick: () -> Unit) {
    TopAppBar(
        title = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("GuardX Home")
            }
        },
        navigationIcon = {
            IconButton(onClick = onMenuClick) {
                Icon(Icons.Filled.Menu, contentDescription = "Menu")
            }
        },
        actions = {
            IconButton(onClick = onProfileClick) {
                Icon(Icons.Filled.AccountCircle, contentDescription = "Profile")
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    GuardXTheme{
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
        HomeScreen(navController, onLogout = { handleLogout() })    }
}