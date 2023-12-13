package org.team9432.scoutingapp.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import org.team9432.scoutingapp.Screen
import org.team9432.scoutingapp.currentScreen
import org.team9432.scoutingapp.currentScreenType
import org.team9432.scoutingapp.ui.screens.MatchSelectionScreen
import org.team9432.scoutingapp.ui.screens.SettingsScreen

@Composable
fun NavigationRail() {
    NavigationRail {
        NavigationRailItem(
            icon = { Icon(Icons.Filled.GridView, contentDescription = "Matches") },
            selected = currentScreenType == Screen.MATCH_SELECTION,
            onClick = {
                currentScreen = { MatchSelectionScreen() }
                currentScreenType = Screen.MATCH_SELECTION
            },
            label = { Text(text = "Matches", style = MaterialTheme.typography.labelLarge) }
        )
        NavigationRailItem(
            icon = { Icon(Icons.Filled.Settings, contentDescription = "Settings") },
            selected = currentScreenType == Screen.SETTINGS || currentScreenType == Screen.DEBUG,
            onClick = {
                currentScreen = { SettingsScreen() }
                currentScreenType = Screen.SETTINGS
            },
            label = { Text(text = "Config", style = MaterialTheme.typography.labelLarge) }
        )
    }
}