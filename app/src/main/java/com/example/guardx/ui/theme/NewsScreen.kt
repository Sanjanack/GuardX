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
fun NewsScreen(navController: NavHostController) {
    // Sample news data
    val newsList = listOf(
        NewsItem("Flood Alert in Coastal Areas", "Authorities have issued a flood alert in coastal areas due to heavy rainfall."),
        NewsItem("Earthquake Preparedness Tips", "Learn what to do before, during, and after an earthquake."),
        NewsItem("Volunteer Training Event", "Join our volunteer training event this weekend to help in disaster management."),
        NewsItem("Hurricane Warning", "A hurricane warning has been issued for the eastern coast. Residents are advised to evacuate."),
        NewsItem("COVID-19 Safety Measures", "Stay updated with the latest COVID-19 guidelines and safety measures."),
        NewsItem("Wildfire Containment Efforts", "Firefighters are making progress in containing the wildfire in the northern region.")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("News & Updates") },
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
                items(newsList) { newsItem ->
                    NewsCard(newsItem = newsItem)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    )
}

@Composable
fun NewsCard(newsItem: NewsItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = newsItem.title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = newsItem.content, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

data class NewsItem(val title: String, val content: String)
