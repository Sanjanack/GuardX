package com.example.guardx.ui.theme

import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String,
        @Query("q") query: String // Added query parameter for topic
    ): NewsResponse
}

// Data classes
data class NewsResponse(val articles: List<Article>)
data class Article(val title: String)