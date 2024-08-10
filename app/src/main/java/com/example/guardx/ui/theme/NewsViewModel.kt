package com.example.guardx.ui.theme

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsViewModel : ViewModel() {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://newsapi.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val newsService = retrofit.create(NewsService::class.java)
    private val _headlines = MutableStateFlow<List<String>>(emptyList())
    val headlines: StateFlow<List<String>> = _headlines
//    val headlines = mutableStateOf<List<String>>(emptyList())

    init {
        viewModelScope.launch {
            val response = newsService.getTopHeadlines("us", "970a1c11903244d1975f684e0301843b", "disaster")
            _headlines.value = response.articles.map { it.title }
        }
    }
}
