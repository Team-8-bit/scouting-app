package org.team9432.scoutingapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.team9432.scoutingapp.io.MatchScoutingSheet
import org.team9432.scoutingapp.io.PitScoutingSheet

const val DEFAULT_EVENT_CODE = "2023azgl"
var appState by mutableStateOf(State())
var matchScoutingData by mutableStateOf<MatchScoutingSheet?>(null)
var pitScoutingData by mutableStateOf<PitScoutingSheet?>(null)

data class State(
    val screen: Screen = Screen.INITIAL_SCREEN,
)

enum class Screen {
    INITIAL_SCREEN
}