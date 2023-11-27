package org.team9432.scoutingapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import org.team9432.scoutingapp.io.SpreadSheet

val appState by mutableStateOf(State())
val workingData by mutableStateOf<SpreadSheet>(emptyList())

data class State(
    val screen: Screen = Screen.INITIAL_SCREEN,
)

enum class Screen {
    INITIAL_SCREEN
}