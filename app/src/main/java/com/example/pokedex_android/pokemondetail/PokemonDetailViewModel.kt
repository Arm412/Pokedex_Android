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
    var id = mutableStateOf(0)
    var dominantColor = mutableStateOf(Color.White)
    var localPokemonData = mutableStateOf<List<PokemonData>>(emptyList())
    var nextEvolution = mutableListOf<PokemonEvolutionData>()
    var prevEvolution = mutableStateOf(PokemonEvolutionData())
    var showShiny = mutableStateOf(false)

    data class PokemonEvolutionData(
        val id: Int = 0,
        val name: String = "",
        val image: String = "",
        val requirement: String = ""
    )

    init {
        fetchLocalPokemonData()
    }

    fun setEvolutionObjects() {
        val currentPokemonData = localPokemonData.value.get(id.value - 1)
        currentPokemonData.evolution.prev?.let {
            prevEvolution.value = createPrevEvolutionObject(currentPokemonData.evolution.prev)
        }

        if (currentPokemonData.evolution.next?.isNotEmpty() == true) {
            nextEvolution = createNextEvolutionObject(currentPokemonData.evolution.next)
        }
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

    fun createNextEvolutionObject(next: List<List<String>>): MutableList<PokemonEvolutionData> {
        val nextEvolutions = mutableListOf<PokemonEvolutionData>()

        for (item in next) {
            val pokemonData = localPokemonData.value[item[0].toInt() - 1]
            nextEvolutions.add(
                PokemonEvolutionData(
                    id = pokemonData.id,
                    name = pokemonData.name.english,
                    image = pokemonData.image.hires,
                    requirement = item[1]
                )
            )
        }
        return nextEvolutions
    }

    fun createPrevEvolutionObject(prev: List<String>): PokemonEvolutionData {
        val pokemonData = localPokemonData.value[prev[0].toInt() - 1]
        return PokemonEvolutionData(
            id = pokemonData.id,
            name = pokemonData.name.english,
            image = pokemonData.image.hires
        )
    }
}
