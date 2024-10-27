package com.example.pokedex_android.repository

import PokemonData
import android.content.Context
import com.example.pokedex_android.data.local.responses.LocalPokemon
import com.example.pokedex_android.data.remote.PokeApi
import com.example.pokedex_android.data.remote.responses.Pokemon
import com.example.pokedex_android.data.remote.responses.PokemonList
import com.example.pokedex_android.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.serialization.json.Json
import java.io.InputStreamReader
import javax.inject.Inject

@ActivityScoped
class PokemonRepository @Inject constructor(
    private val context: Context,
    private val api: PokeApi
){

    suspend fun getPokemonList(limit: Int, offset: Int): Resource<PokemonList> {
        val response = try {
            api.getPokemonList(limit, offset)
        } catch(e: Exception) {
            return Resource.Error("An unknown error occurred")
        }
        return Resource.Success(response)
    }

    suspend fun getPokemonInfo(pokemonName: String): Resource<Pokemon> {
        val response = try {
            api.getPokemonInfo(pokemonName)
        } catch(e: Exception) {
            return Resource.Error("An unknown error occurred")
        }
        return Resource.Success(response)
    }

    fun loadPokemonJson(): List<PokemonData> {
        val jsonString = context.assets.open("pokedex.json").use { inputStream ->
            InputStreamReader(inputStream).readText()
        }
        val withUnknownKeys = Json { ignoreUnknownKeys = true }
        return withUnknownKeys.decodeFromString(jsonString)
    }
}
