package com.example.pokedex_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pokedex_android.pokemondetail.PokemonDetailScreen
import com.example.pokedex_android.pokemonlist.PokemonListScreen
import com.example.pokedex_android.ui.theme.AndroidPokedexTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidPokedexTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "pokemon_list_screen"
                ) {
                    composable("pokemon_list_screen") {
                        PokemonListScreen(navController = navController)
                    }

                    composable(
                        "pokemon_detail_screen/{pokemonName}/{id}/{showShiny}",
                        arguments = listOf(
                            navArgument("pokemonName") {
                                type = NavType.StringType
                            },
                            navArgument("id") {
                                type = NavType.IntType
                            },
                            navArgument("showShiny") {
                                type = NavType.BoolType
                            }
                        )
                    ) {
                        val dominantColor = remember {
                            val color = it.arguments?.getInt("dominantColor")
                            color?.let { Color(it) } ?: Color.White
                        }

                        val pokemonName = remember {
                            it.arguments?.getString("pokemonName")
                        }

                        val id = remember {
                            it.arguments?.getInt("id") ?: 0
                        }

                        val showShiny = remember {
                            it.arguments?.getBoolean("showShiny")
                        }
                        PokemonDetailScreen(
                            id = id,
                            dominantColor = dominantColor,
                            pokemonName = pokemonName?.lowercase(Locale.ROOT) ?: "",
                            showShiny = showShiny ?: false,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidPokedexTheme {
        Greeting("Android")
    }
}
