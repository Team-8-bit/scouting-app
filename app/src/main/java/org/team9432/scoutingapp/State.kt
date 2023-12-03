package org.team9432.scoutingapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

var appScreen by mutableStateOf(Screen.MATCH_SELECTION)
var currentMatch by mutableIntStateOf(-1)

enum class Screen {
    SETTINGS,
    DEBUG,
    MATCH_SELECTION,
    MATCH_SCOUTING_SCREEN,
}