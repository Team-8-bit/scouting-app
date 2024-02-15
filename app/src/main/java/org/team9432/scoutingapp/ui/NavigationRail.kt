package org.team9432.scoutingapp.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import org.team9432.scoutingapp.setAppScreen
import org.team9432.scoutingapp.ui.screens.MatchSelectionScreen
import org.team9432.scoutingapp.ui.screens.SettingsScreen

@Composable
fun NavigationRail() {
    NavigationRail {
        var currentlySelected by remember { mutableIntStateOf(0) }
        NavigationRailItem(
            icon = { Icon(Icons.Filled.GridView, contentDescription = "Matches") },
            selected = currentlySelected == 0,
            onClick = {
                setAppScreen { MatchSelectionScreen() }
                currentlySelected = 0
            },
            label = { Text(text = "Matches", style = MaterialTheme.typography.labelLarge) }
        )
        NavigationRailItem(
            icon = { Icon(Icons.Filled.Settings, contentDescription = "Settings") },
            selected = currentlySelected == 1,
            onClick = {
                setAppScreen { SettingsScreen() }
                currentlySelected = 1
            },
            label = { Text(text = "Config", style = MaterialTheme.typography.labelLarge) }
        )
    }
}