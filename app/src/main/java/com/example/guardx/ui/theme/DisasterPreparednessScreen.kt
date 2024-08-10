package com.example.guardx.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisasterPreparednessScreen(navController: NavHostController) {
    // Sample disaster preparedness data
    val preparednessTips = listOf(
        PreparednessItem("Earthquake", "Secure heavy furniture and appliances. Identify safe spots in each room like under sturdy tables."),
        PreparednessItem("Flood", "Know the flood risk in your area. Keep important documents in a waterproof container."),
        PreparednessItem("Hurricane", "Have an emergency kit ready with food, water, and essential supplies. Board up windows and doors."),
        PreparednessItem("Tornado", "Identify a safe room or storm shelter in your home. Practice tornado drills with your family."),
        PreparednessItem("Wildfire", "Create a defensible space around your home by clearing flammable vegetation. Prepare an evacuation plan."),
        PreparednessItem("Pandemic", "Stock up on necessary medicines and hygiene supplies. Follow public health guidelines for social distancing."),
        PreparednessItem("General Tips", "Prepare an emergency kit, make a family communication plan, and stay informed about local alerts.")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Disaster Preparedness") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                items(preparednessTips) { preparednessItem ->
                    PreparednessCard(preparednessItem = preparednessItem)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    )
}

@Composable
fun PreparednessCard(preparednessItem: PreparednessItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = preparednessItem.title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = preparednessItem.content, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

data class PreparednessItem(val title: String, val content: String)
