package org.team9432.scoutingapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.team9432.scoutingapp.Screen
import org.team9432.scoutingapp.appScreen
import org.team9432.scoutingapp.currentMatch
import org.team9432.scoutingapp.io.MatchScoutingFile.hasBeenScouted
import org.team9432.scoutingapp.io.ScheduleFiles
import org.team9432.scoutingapp.io.config
import org.team9432.scoutingapp.io.data.ScheduledMatch

@Composable
fun MatchSelectionScreen() {
    LazyColumn(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        ScheduleFiles.getMatches(config.scoutID, config.eventID).forEach {
            item {
                MatchDisplay(
                    data = it,
                    hasBeenScouted = it.hasBeenScouted(it.teams[config.scoutID]!!.teamNumber),
                    onClick = {
                        currentMatch = it.number
                        appScreen = Screen.MATCH_SCOUTING_SCREEN
                    })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MatchDisplay(data: ScheduledMatch, onClick: () -> Unit, enabled: Boolean = true, hasBeenScouted: Boolean = false) {
    ListItem(
        modifier = Modifier.clickable(enabled = enabled, onClick = onClick),
        headlineText = {
            Text("Match Number ${data.number}")
        },
        supportingText = {
            Text("Team to scout: ${data.teams[config.scoutID]?.teamNumber}")
        },
        colors = if (enabled && !hasBeenScouted) {
            ListItemDefaults.colors()
        } else {
            val disabledColor = ListItemDefaults.contentColor.copy(0.6F)
            ListItemDefaults.colors(supportingColor = disabledColor, headlineColor = disabledColor)
        }
    )
}