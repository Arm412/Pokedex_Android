package com.example.pokedex_android.data.local.responses

data class Evolution(
    val next: List<List<String>>,
    val prev: List<String>
)