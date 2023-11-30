package org.team9432.scoutingapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
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
fun MatchSelectionScreen(matches: List<ScheduledMatch>, scoutID: Int) {
    val alreadyScoutedMatches = matches.filter { it.hasBeenScouted(it.teams[scoutID]!!.teamNumber) }
    val toBeScoutedMatches = matches.filter { !alreadyScoutedMatches.contains(it) }

    val scrollState = LazyListState(firstVisibleItemIndex = alreadyScoutedMatches.size + 1)
    LazyColumn(Modifier.fillMaxSize(), state = scrollState, horizontalAlignment = Alignment.CenterHorizontally) {
        alreadyScoutedMatches.forEach {
            item {
                MatchDisplay(
                    data = it,
                    hasBeenScouted = true,
                    onClick = {
                        currentMatch = it.number
                        appScreen = Screen.MATCH_SCOUTING_SCREEN
                    }
                )
            }
        }
        item {
            Divider()
        }
        toBeScoutedMatches.forEach {
            item {
                MatchDisplay(
                    data = it,
                    hasBeenScouted = false,
                    onClick = {
                        currentMatch = it.number
                        appScreen = Screen.MATCH_SCOUTING_SCREEN
                    }
                )
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