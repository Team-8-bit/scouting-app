package org.team9432.scoutingapp

import androidx.compose.runtime.*
import org.team9432.scoutingapp.ui.screens.QRCodeScreen

var currentScreen by mutableStateOf<@Composable () -> Unit>({ QRCodeScreen(team = "6656", matchNumber = 1) })
var currentScreenType by mutableStateOf(Screen.QR_CODE_SCREEN)

enum class Screen {
    SETTINGS,
    DEBUG,
    MATCH_SELECTION,
    MATCH_SCOUTING_SCREEN,
    QR_CODE_SCREEN,
}