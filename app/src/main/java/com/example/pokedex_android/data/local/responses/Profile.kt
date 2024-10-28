package com.example.pokedex_android.data.local.responses

data class Profile(
    val ability: List<List<String>>,
    val egg: List<String>,
    val gender: String,
    val height: String,
    val weight: String
)