package com.example.pokedex_android.data.local.responses

data class PokemonItem(
    val base: Base,
    val description: String,
    val evolution: Evolution,
    val id: Int,
    val image: Image,
    val name: Name,
    val profile: Profile,
    val species: String,
    val type: List<String>
)