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
fun FirstAidScreen(navController: NavHostController) {
    val firstAidTips = listOf(
        FirstAidItem("CPR", "Place the heel of your hand on the center of the chest. Place your other hand on top. Press down hard and fast."),
        FirstAidItem("Bleeding", "Apply direct pressure on the wound with a clean cloth or bandage. Elevate the injured part."),
        FirstAidItem("Burns", "Cool the burn under running water for at least 10 minutes. Cover with a sterile dressing."),
        FirstAidItem("Fractures", "Immobilize the affected area with a splint. Avoid moving the injured limb."),
        FirstAidItem("Choking", "Give 5 back blows between the shoulder blades with the heel of your hand. If unsuccessful, give 5 abdominal thrusts."),
        FirstAidItem("Shock", "Lay the person down and elevate their legs. Keep them warm and calm."),
        FirstAidItem("Snake Bite", "Keep the person calm and immobile. Keep the bite area below heart level. Seek medical help immediately.")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("First Aid") },
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
                items(firstAidTips) { firstAidItem ->
                    FirstAidCard(firstAidItem = firstAidItem)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    )
}

@Composable
fun FirstAidCard(firstAidItem: FirstAidItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = firstAidItem.title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = firstAidItem.content, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

data class FirstAidItem(val title: String, val content: String)
