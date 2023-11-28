package org.team9432.scoutingapp.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import org.team9432.scoutingapp.Screen
import org.team9432.scoutingapp.appState

@Composable
fun NavigationRail() {
    var selectedItem by remember { mutableStateOf(0) }
    NavigationRail {
        NavigationRailItem(
            icon = { Icon(Icons.Filled.FileUpload, contentDescription = "Choose File") },
            selected = selectedItem == 0,
            onClick = { selectedItem = 0; appState = appState.copy(screen = Screen.INITIAL_SCREEN) },
            label = { Text(text = "Open File", style = MaterialTheme.typography.labelLarge)}
        )
    }
}