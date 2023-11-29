package org.team9432.scoutingapp.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import org.team9432.scoutingapp.Screen
import org.team9432.scoutingapp.appScreen
import org.team9432.scoutingapp.io.config
import org.team9432.scoutingapp.io.updateConfig

@Composable
fun NavigationRail() {
    var selectedItem by remember { mutableIntStateOf(0) }
    NavigationRail {
        NavigationRailItem(
            icon = { Icon(Icons.Filled.GridView, contentDescription = "Matches") },
            selected = selectedItem == 1,
            onClick = { selectedItem = 1; appScreen = Screen.MATCH_SELECTION },
            label = { Text(text = "Matches", style = MaterialTheme.typography.labelLarge) }
        )
        NavigationRailItem(
            icon = { Icon(Icons.Filled.Settings, contentDescription = "Settings") },
            selected = selectedItem == 2,
            onClick = { selectedItem = 2; appScreen = Screen.SETTINGS },
            label = { Text(text = "Config", style = MaterialTheme.typography.labelLarge) }
        )
    }
}