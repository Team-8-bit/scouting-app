package org.team9432.scoutingapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NavigateBefore
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.team9432.scoutingapp.io.MatchStorageInterface
import org.team9432.scoutingapp.io.QRCodes
import org.team9432.scoutingapp.io.ScheduleInterface
import org.team9432.scoutingapp.io.config
import org.team9432.scoutingapp.io.json.DataType
import org.team9432.scoutingapp.setAppScreen

@Composable
fun QRCodeScreen(team: String, matchNumber: String, dataType: DataType) {
    val QRString = MatchStorageInterface.getMatchData(team, matchNumber, dataType).getSerializedQROutput()
    val bitmap = QRCodes.fromString(QRString)

    Row(Modifier.fillMaxSize().padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
        Column(Modifier.fillMaxHeight().fillMaxWidth(0.5F), horizontalAlignment = Alignment.CenterHorizontally) {
            Image(modifier = Modifier.padding(10.dp), bitmap = bitmap, contentDescription = null)

            FilledTonalButton(onClick = { setAppScreen { MatchSelectionScreen() } }) {
                Text(text = "Done")
            }
        }
        Column(Modifier.padding(10.dp).fillMaxWidth().fillMaxHeight(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Text(text = "Match $matchNumber", style = MaterialTheme.typography.headlineLarge)
            Text(text = "Team $team", style = MaterialTheme.typography.headlineLarge)

            val previousMatchNumber = (matchNumber.toInt() - 1).toString()
            val nextMatchNumber = (matchNumber.toInt() + 1).toString()

            val nextMatchTeam = ScheduleInterface.getMatches()[nextMatchNumber]
            val previousMatchTeam = ScheduleInterface.getMatches()[previousMatchNumber]

            val nextEnabled = nextMatchTeam?.let { MatchStorageInterface.matchDataExists(it, nextMatchNumber, dataType) } == true
            val backEnabled = previousMatchTeam?.let { MatchStorageInterface.matchDataExists(it, previousMatchNumber, dataType) } == true

            val enabledBackground = MaterialTheme.colorScheme.tertiaryContainer
            val disabledBackground = enabledBackground.copy(0.7F)
            val icon = MaterialTheme.colorScheme.contentColorFor(MaterialTheme.colorScheme.tertiaryContainer)

            Row(Modifier.fillMaxHeight(0.5F), verticalAlignment = Alignment.CenterVertically) {
                Box(
                    Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(0.5F)
                        .padding(top = 5.dp, end = 2.5.dp, bottom = 5.dp, start = 5.dp)
                        .background(if (backEnabled) enabledBackground else disabledBackground, MaterialTheme.shapes.small)
                        .clickable(onClick = { setAppScreen { QRCodeScreen(team = previousMatchTeam!!, matchNumber = previousMatchNumber, dataType = dataType) } }, enabled = backEnabled),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Filled.NavigateBefore, contentDescription = "Back", tint = icon)
                }
                Box(
                    Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .padding(top = 5.dp, end = 2.5.dp, bottom = 5.dp, start = 5.dp)
                        .background(if (nextEnabled) enabledBackground else disabledBackground, MaterialTheme.shapes.small)
                        .clickable(onClick = { setAppScreen { QRCodeScreen(team = nextMatchTeam!!, matchNumber = nextMatchNumber, dataType = dataType) } }, enabled = nextEnabled),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Filled.NavigateNext, contentDescription = "Next", tint = icon)
                }
            }

            if (config.debugMode) Text(QRString)
        }
    }
}