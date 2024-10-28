package com.example.pokedex_android.pokemondetail

import PokemonData
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex_android.data.remote.responses.Pokemon
import com.example.pokedex_android.repository.PokemonRepository
import com.example.pokedex_android.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val repository: PokemonRepository
): ViewModel() {
    var localPokemonData = mutableStateOf<List<PokemonData>>(emptyList())

    init {
        fetchLocalPokemonData()
    }

    suspend fun getPokemonInfo(pokemonName: String): Resource<Pokemon> {
        return repository.getPokemonInfo(pokemonName)
    }

    fun fetchLocalPokemonData() {
        viewModelScope.launch {
            localPokemonData.value = repository.loadPokemonJson()
        }
    }
}
