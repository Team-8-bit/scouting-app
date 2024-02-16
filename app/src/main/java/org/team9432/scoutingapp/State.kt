package org.team9432.scoutingapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.team9432.scoutingapp.ui.screens.MatchSelectionScreen

var currentScreen by mutableStateOf<@Composable () -> Unit>({ MatchSelectionScreen() })
    private set
var currentIsFullscreen by mutableStateOf(false)
    private set

fun setAppScreen(fullscreen: Boolean = false, screen: @Composable () -> Unit) {
    currentScreen = screen
    currentIsFullscreen = fullscreen
}
