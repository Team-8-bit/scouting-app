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
import org.team9432.scoutingapp.Screen
import org.team9432.scoutingapp.appScreen
import org.team9432.scoutingapp.io.config
import org.team9432.scoutingapp.io.updateConfig

@Composable
fun SettingsScreen() {
    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(Modifier.fillMaxWidth().padding(10.dp)) {
            Text(
                text = "Config",
                style = MaterialTheme.typography.headlineLarge
            )
            Spacer(Modifier.weight(1F))
            TextButton(onClick = { appScreen = Screen.DEBUG }) {
                Text("debug screen")
            }
        }
        ToggleOption(
            initialState = config.darkMode,
            onSet = { updateConfig(config.copy(darkMode = it)) },
            title = "Dark Mode",
        )
        TextOption(initialState = config.eventID, onSet = { updateConfig(config.copy(eventID = it)) }, title = "Event ID")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TextOption(initialState: String, onSet: (String) -> Unit, title: String, enabled: Boolean = true) {
    Surface(Modifier.padding(10.dp), shape = MaterialTheme.shapes.medium, color = MaterialTheme.colorScheme.background) {
        Row(Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
            var currentValue by remember { mutableStateOf(initialState) }
            Text(text = title)
            Spacer(modifier = Modifier.weight(1F))

            val focusManager = LocalFocusManager.current

            OutlinedTextField(
                enabled = enabled,
                value = currentValue,
                onValueChange = { currentValue = it },
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