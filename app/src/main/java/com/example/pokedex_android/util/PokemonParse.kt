package com.plcoding.jetpackcomposepokedex.util

import androidx.compose.ui.graphics.Color
import com.example.pokedex_android.data.remote.responses.Stat
import com.example.pokedex_android.data.remote.responses.Type
import com.example.pokedex_android.ui.theme.AtkColor
import com.example.pokedex_android.ui.theme.DefColor
import com.example.pokedex_android.ui.theme.HPColor
import com.example.pokedex_android.ui.theme.SpAtkColor
import com.example.pokedex_android.ui.theme.SpDefColor
import com.example.pokedex_android.ui.theme.SpdColor
import com.example.pokedex_android.ui.theme.TypeBug
import com.example.pokedex_android.ui.theme.TypeDark
import com.example.pokedex_android.ui.theme.TypeDragon
import com.example.pokedex_android.ui.theme.TypeElectric
import com.example.pokedex_android.ui.theme.TypeFairy
import com.example.pokedex_android.ui.theme.TypeFighting
import com.example.pokedex_android.ui.theme.TypeFire
import com.example.pokedex_android.ui.theme.TypeFlying
import com.example.pokedex_android.ui.theme.TypeGhost
import com.example.pokedex_android.ui.theme.TypeGrass
import com.example.pokedex_android.ui.theme.TypeGround
import com.example.pokedex_android.ui.theme.TypeIce
import com.example.pokedex_android.ui.theme.TypeNormal
import com.example.pokedex_android.ui.theme.TypePoison
import com.example.pokedex_android.ui.theme.TypePsychic
import com.example.pokedex_android.ui.theme.TypeRock
import com.example.pokedex_android.ui.theme.TypeSteel
import com.example.pokedex_android.ui.theme.TypeWater
import java.util.Locale

fun parseTypeToColor(type: Type): Color {
    return when(type.type.name.lowercase(Locale.ROOT)) {
        "normal" -> TypeNormal
        "fire" -> TypeFire
        "water" -> TypeWater
        "electric" -> TypeElectric
        "grass" -> TypeGrass
        "ice" -> TypeIce
        "fighting" -> TypeFighting
        "poison" -> TypePoison
        "ground" -> TypeGround
        "flying" -> TypeFlying
        "psychic" -> TypePsychic
        "bug" -> TypeBug
        "rock" -> TypeRock
        "ghost" -> TypeGhost
        "dragon" -> TypeDragon
        "dark" -> TypeDark
        "steel" -> TypeSteel
        "fairy" -> TypeFairy
        else -> Color.Black
    }
}

fun parseStatToColor(stat: Stat): Color {
    return when(stat.stat.name.toLowerCase(Locale.ROOT)) {
        "hp" -> HPColor
        "attack" -> AtkColor
        "defense" -> DefColor
        "special-attack" -> SpAtkColor
        "special-defense" -> SpDefColor
        "speed" -> SpdColor
        else -> Color.White
    }
}

fun parseStatToAbbr(stat: Stat): String {
    return when(stat.stat.name.toLowerCase(Locale.ROOT)) {
        "hp" -> "HP"
        "attack" -> "Atk"
        "defense" -> "Def"
        "special-attack" -> "SpAtk"
        "special-defense" -> "SpDef"
        "speed" -> "Spd"
        else -> ""
    }
}
