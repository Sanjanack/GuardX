package com.example.guardx.ui.theme

data class WeatherResponse(
    val weather: List<Weather>
)

data class Weather(
    val description: String
)
