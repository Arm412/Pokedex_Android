package com.example.pokedex_android.pokemondetail

import PokemonData
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.example.pokedex_android.data.local.responses.Evolution
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
    var dominantColor = mutableStateOf(Color.White)
    var localPokemonData = mutableStateOf<List<PokemonData>>(emptyList())
    var showShiny = mutableStateOf(false)

    data class PokemonEvolutionData(
        val id: Int,
        val name: String,
        val image: String,
    )

    init {
        fetchLocalPokemonData()
    }

    suspend fun getPokemonInfo(pokemonName: String): Resource<Pokemon> {
        return repository.getPokemonInfo(pokemonName)
    }

    fun calcDominantColor(drawable: Drawable, onFinish: (Color) -> Unit) {
        val bmp = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)

        Palette.from(bmp).generate { palette ->
            palette?.dominantSwatch?.rgb?.let { colorValue ->
                onFinish(Color(colorValue))
            }
        }
    }

    private fun fetchLocalPokemonData() {
        viewModelScope.launch {
            localPokemonData.value = repository.loadPokemonJson()
        }
    }

    fun toggleShiny() {
        showShiny.value = !showShiny.value
    }
}
