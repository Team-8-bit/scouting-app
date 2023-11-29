package org.team9432.scoutingapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

var appScreen by mutableStateOf(Screen.SETTINGS)

enum class Screen {
    SETTINGS,
    DEBUG
}