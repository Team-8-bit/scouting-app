package org.team9432.scoutingapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.team9432.scoutingapp.Screen
import org.team9432.scoutingapp.currentScreen
import org.team9432.scoutingapp.currentScreenType
import org.team9432.scoutingapp.io.SDCard

@Composable
fun DebugScreen() {
    Column(Modifier.fillMaxSize()) {
        Row(Modifier.fillMaxWidth().padding(10.dp)) {
            Text(
                text = "Debug",
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Start
            )
            Spacer(Modifier.weight(1F))
            TextButton(onClick = {
                currentScreen = { SettingsScreen() }
                currentScreenType = Screen.SETTINGS
            }) {
                Text("back")
            }
        }
        Column(Modifier.padding(10.dp)) {
            Text(text = "Match Scouting File", style = MaterialTheme.typography.labelLarge)
            Text(text = SDCard.getMatchScheduleFile().absolutePath)
        }
        Column(Modifier.padding(10.dp)) {
            Text(text = "Pit Scouting File", style = MaterialTheme.typography.labelLarge)
            Text(text = SDCard.getPitScheduleFile().absolutePath)
        }
    }
}