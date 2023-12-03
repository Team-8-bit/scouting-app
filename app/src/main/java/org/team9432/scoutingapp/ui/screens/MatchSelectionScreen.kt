package org.team9432.scoutingapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.team9432.scoutingapp.Screen
import org.team9432.scoutingapp.appScreen
import org.team9432.scoutingapp.currentMatch
import org.team9432.scoutingapp.io.MatchScoutingFile
import org.team9432.scoutingapp.io.MatchScoutingFile.hasBeenScouted

@Composable
fun MatchSelectionScreen(matches: Map<Int, String>) {
    val alreadyScoutedMatches = matches.filter { hasBeenScouted(it.key, it.value) }
    val toBeScoutedMatches = matches.filterNot { alreadyScoutedMatches.contains(it.key) }

    val scrollState = LazyListState(firstVisibleItemIndex = alreadyScoutedMatches.size + 1)
    LazyColumn(Modifier.fillMaxSize(), state = scrollState, horizontalAlignment = Alignment.CenterHorizontally) {
        alreadyScoutedMatches.forEach {
            item {
                MatchDisplay(
                    matchNumber = it.key,
                    teamToScout = it.value,
                    hasBeenScouted = true,
                    onClick = {
                        currentMatch = it.key
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
                    matchNumber = it.key,
                    teamToScout = it.value,
                    hasBeenScouted = false,
                    onClick = {
                        currentMatch = it.key
                        appScreen = Screen.MATCH_SCOUTING_SCREEN
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MatchDisplay(matchNumber: Int, teamToScout: String, onClick: () -> Unit, enabled: Boolean = true, hasBeenScouted: Boolean = false) {
    var showMoreOptions by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    val enabledColor = MaterialTheme.colorScheme.contentColorFor(MaterialTheme.colorScheme.surface)
    val disabledColor = enabledColor.copy(0.6F)
    val contentColor = if (enabled && !hasBeenScouted) enabledColor else disabledColor

    ListItem(
        modifier = Modifier.clickable(enabled = enabled, onClick = onClick),
        headlineText = {
            Text("Match Number $matchNumber")
        },
        supportingText = {
            Text("Team to scout: $teamToScout")
        },
        colors = ListItemDefaults.colors(supportingColor = contentColor, headlineColor = contentColor),
        trailingContent = {
            Box {
                IconButton(onClick = { showMoreOptions = true }) {
                    Icon(Icons.Filled.MoreHoriz, "More Options", tint = contentColor)
                }
                DropdownMenu(
                    modifier = Modifier,
                    expanded = showMoreOptions,
                    onDismissRequest = { showMoreOptions = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Delete") },
                        onClick = { showMoreOptions = false; showDeleteDialog = true },
                        trailingIcon = { Icon(Icons.Filled.Delete, "Delete") }
                    )
                }
            }
        }
    )
    if (showDeleteDialog) {
        AlertDialog(
            title = { Text(text = "Delete Data") },
            text = { Text(text = "This will permanently delete any saved data for this match") },
            onDismissRequest = { showDeleteDialog = false },
            confirmButton = { TextButton(onClick = { MatchScoutingFile.deleteMatchData(teamToScout, matchNumber); showDeleteDialog = false }) { Text("Confirm") } },
            dismissButton = { TextButton(onClick = { showDeleteDialog = false }) { Text("Cancel") } }
        )
    }
}