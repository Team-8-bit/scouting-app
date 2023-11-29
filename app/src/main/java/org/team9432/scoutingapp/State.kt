package org.team9432.scoutingapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

var appState by mutableStateOf(State())

data class State(
    val screen: Screen = Screen.SETTINGS,
)

enum class Screen {
    SETTINGS,
    DEBUG
}