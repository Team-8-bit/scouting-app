package org.team9432.scoutingapp

import androidx.compose.runtime.*
import org.team9432.scoutingapp.ui.screens.QRCodeScreen

var currentScreen by mutableStateOf<@Composable () -> Unit>({ QRCodeScreen(team = "6656", matchNumber = 1) })