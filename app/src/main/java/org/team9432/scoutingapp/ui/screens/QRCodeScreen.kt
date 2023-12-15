package org.team9432.scoutingapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.team9432.scoutingapp.currentScreen
import org.team9432.scoutingapp.io.MatchScoutingFile
import org.team9432.scoutingapp.io.QRCodes

@Composable
fun QRCodeScreen(team: String, matchNumber: Int) {
    val bitmap = QRCodes.fromString(MatchScoutingFile.getMatchJSON(matchNumber, team))

    Row(Modifier.fillMaxSize().padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
        Image(modifier = Modifier.padding(10.dp), bitmap = bitmap, contentDescription = null)
        Column(Modifier.padding(10.dp).fillMaxWidth().fillMaxHeight(0.5F), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Match $matchNumber", style = MaterialTheme.typography.headlineLarge)
            Text(text = "Team $team", style = MaterialTheme.typography.headlineLarge)

            Spacer(modifier = Modifier.weight(1F))

            FilledTonalButton(onClick = {
                currentScreen = { MatchSelectionScreen() }
            }) {
                Text(text = "Back")
            }
        }
    }
}