package org.team9432.scoutingapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import org.team9432.scoutingapp.io.config
import org.team9432.scoutingapp.io.updateConfig
import org.team9432.scoutingapp.setAppScreen

@Composable
fun SettingsScreen() {
    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(Modifier.fillMaxWidth().padding(10.dp)) {
            Text(
                text = "Config",
                style = MaterialTheme.typography.headlineLarge
            )
            Spacer(Modifier.weight(1F))
//            TextButton(onClick = { setAppScreen { DebugScreen() } }) {
//                Text("debug screen")
//            }
        }
        ToggleOption(
            initialState = config.darkMode,
            onSet = { updateConfig(config.copy(darkMode = it)) },
            title = "Dark Mode",
        )
        TextOption(
            initialState = config.eventID,
            onSet = { updateConfig(config.copy(eventID = it)) },
            title = "Event ID"
        )
        TextOption(
            initialState = config.scoutID.toString(),
            onSet = { updateConfig(config.copy(scoutID = it.toInt())) },
            title = "Scout ID",
            predicate = { it.isDigitsOnly() }
        )
        ToggleOption(
            initialState = config.isSuperscout,
            onSet = { updateConfig(config.copy(isSuperscout = it)) },
            enabled = config.debugMode,
            title = "Subjective Scouter",
        )
        ToggleOption(
            initialState = config.debugMode,
            onSet = { updateConfig(config.copy(debugMode = it)) },
            title = "Debug Mode (probably don't touch this)",
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TextOption(initialState: String, onSet: (String) -> Unit, title: String, enabled: Boolean = true, predicate: (String) -> Boolean = { true }) {
    Surface(Modifier.padding(10.dp), shape = MaterialTheme.shapes.medium, color = MaterialTheme.colorScheme.background) {
        Row(Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
            var currentValue by remember { mutableStateOf(initialState) }
            Text(text = title)
            Spacer(modifier = Modifier.weight(1F))

            val focusManager = LocalFocusManager.current

            OutlinedTextField(
                enabled = enabled,
                value = currentValue,
                onValueChange = {
                    if (predicate(it)) {
                        currentValue = it
                    }
                },
                singleLine = true,
                trailingIcon = {
                    if (enabled) {
                        Icon(
                            Icons.Filled.Check,
                            contentDescription = "Confirm",
                            modifier = Modifier.clickable(
                                onClick = {
                                    focusManager.clearFocus()
                                    onSet(currentValue)
                                }
                            )
                        )
                    }
                }
            )
        }
    }
}

@Composable
private fun ToggleOption(initialState: Boolean, onSet: (Boolean) -> Unit, title: String, enabled: Boolean = true) {
    Surface(Modifier.padding(10.dp), shape = MaterialTheme.shapes.medium, color = MaterialTheme.colorScheme.background) {
        Row(Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(text = title)
            Spacer(modifier = Modifier.weight(1F))
            Switch(checked = initialState, onCheckedChange = onSet, enabled = enabled)
        }
    }
}