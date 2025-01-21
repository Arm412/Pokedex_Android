package com.example.pokedex_android.pokemondetail

import PokemonData
import PokemonEffectiveness
import PokemonTypeEffectiveness
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.runtime.mutableIntStateOf
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
    var id = mutableIntStateOf(0)
    var dominantColor = mutableStateOf(Color.White)
    var localPokemonData = mutableStateOf<List<PokemonData>>(emptyList())
    var pokemonEffectivenessData = mutableStateOf(PokemonEffectiveness(emptyMap()))
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

    fun determineTypeEffectivenessGroups(types: List<String>): MutableMap<String, MutableMap<String, MutableList<String>>> {
        val typeEffectivenessMap = mutableMapOf(
            "offensive" to mutableMapOf<String, MutableList<String>>(),
            "defensive" to mutableMapOf()
        )

        for(type in pokemonEffectivenessData.value.types.keys) {
            var totalMultiplicativeOffense = 0.0
            var totalMultiplicativeDefense = 1.0

            for (currentPokemonType in types) {

                var typeBattleInfo = pokemonEffectivenessData.value.types[currentPokemonType]
                var typeOffensiveMultiplier: Number = typeBattleInfo?.offensive?.get(type) ?: 0
                var typeDefensiveMultiplier: Number = typeBattleInfo?.defensive?.get(type) ?: 0

                if (typeOffensiveMultiplier.toDouble() > totalMultiplicativeOffense) {
                    totalMultiplicativeOffense = typeOffensiveMultiplier.toDouble()
                }
                
                totalMultiplicativeDefense *= typeDefensiveMultiplier.toDouble()
            }
            val offenseKey = totalMultiplicativeOffense.toString()

            typeEffectivenessMap["offensive"]?.get(offenseKey)?.apply {
                add(type)
            } ?: run {
                typeEffectivenessMap["offensive"]?.put(offenseKey, mutableListOf(type))
            }

            val defenseKey = totalMultiplicativeDefense.toString()

            typeEffectivenessMap["defensive"]?.get(defenseKey)?.apply {
                add(type)
            } ?: run {
                typeEffectivenessMap["defensive"]?.put(defenseKey, mutableListOf(type))
            }

        }
        return typeEffectivenessMap
    }

    private fun fetchLocalPokemonData() {
        viewModelScope.launch {
            localPokemonData.value = repository.loadPokemonJson()
            pokemonEffectivenessData.value = repository.loadPokemonEffectiveness()
        }
    }

    fun toggleShiny() {
        showShiny.value = !showShiny.value
    }

    private fun createNextEvolutionObject(next: List<List<String>>): MutableList<PokemonEvolutionData> {
        val nextEvolutions = mutableListOf<PokemonEvolutionData>()

        for (item in next) {
            val pokemonData = localPokemonData.value[item[0].toInt() - 1]
            nextEvolutions.add(
                PokemonEvolutionData(
                    id = pokemonData.id,
                    name = pokemonData.name.english,
                    image = pokemonData.image.hires,
                    requirement = item[1].replaceFirstChar(Char::titlecase)
                )
            )
        }
        return nextEvolutions
    }

    private fun createPrevEvolutionObject(prev: List<String>): PokemonEvolutionData {
        val pokemonData = localPokemonData.value[prev[0].toInt() - 1]
        return PokemonEvolutionData(
            id = pokemonData.id,
            name = pokemonData.name.english,
            image = pokemonData.image.hires
        )
    }
}
