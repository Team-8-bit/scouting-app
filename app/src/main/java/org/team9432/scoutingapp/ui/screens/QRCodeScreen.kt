package org.team9432.scoutingapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.team9432.scoutingapp.io.MatchScoutingFile
import org.team9432.scoutingapp.io.QRCodes

@Composable
fun QRCodeScreen(team: String, matchNumber: Int) {
    val bitmap = QRCodes.fromString(MatchScoutingFile.getMatchJSON(matchNumber, team))

    Row(Modifier.fillMaxSize().padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
        Image(modifier = Modifier.padding(10.dp), bitmap = bitmap, contentDescription = null)
        Column(Modifier.padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {

        }
    }
}