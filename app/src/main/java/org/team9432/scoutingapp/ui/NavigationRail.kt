package org.team9432.scoutingapp.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import org.team9432.scoutingapp.Screen
import org.team9432.scoutingapp.appState
import org.team9432.scoutingapp.io.config
import org.team9432.scoutingapp.io.updateConfig

@Composable
fun NavigationRail() {
    var selectedItem by remember { mutableStateOf(0) }
    NavigationRail {
        NavigationRailItem(
            icon = { Icon(Icons.Filled.Settings, contentDescription = "Settings") },
            selected = selectedItem == 1,
            onClick = { selectedItem = 1; appState = appState.copy(screen = Screen.SETTINGS) },
            label = { Text(text = "Config", style = MaterialTheme.typography.labelLarge) }
        )
        NavigationRailItem(
            icon = {
                if (config.darkMode) {
                    Icon(Icons.Filled.LightMode, contentDescription = "Light Mode")
                } else {
                    Icon(Icons.Filled.DarkMode, contentDescription = "Dark Mode")
                }
            },
            onClick = {
                updateConfig(config.copy(darkMode = !config.darkMode))
            },
            selected = false
        )
    }
}