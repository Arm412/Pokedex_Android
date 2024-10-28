package com.example.pokedex_android.pokemonlist.dropdown

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.math.exp

enum class RowItemSize(val itemsInRow: Int) {
    SMALL(3),
    MEDIUM(2),
    LARGE(1)
}

@Composable
fun PokemonListDropDownMenu(
    callback: (RowItemSize) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedSize by remember { mutableStateOf("Medium") }

    Box(modifier = Modifier
        .clickable {
            expanded = !expanded
        }
        .background(color = Color.White)
        .padding(start = 16.dp, top = 4.dp, end = 0.dp, bottom = 4.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = selectedSize
            )
            IconButton(onClick = { expanded = true }) {
                Icon(Icons.Default.ArrowDropDown, contentDescription = null)
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
                ) {
                DropdownMenuItem(
                    text = { Text("Small") },
                    onClick = {
                        selectedSize = "Small"
                        callback(RowItemSize.SMALL)
                        expanded = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("Medium") },
                    onClick = {
                        selectedSize = "Medium"
                        callback(RowItemSize.MEDIUM)
                        expanded = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("Large") },
                    onClick = {
                        selectedSize = "Large"
                        callback(RowItemSize.LARGE)
                        expanded = false
                    }
                )
            }
        }
    }
}
