package org.team9432.scoutingapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.team9432.scoutingapp.io.MatchStorageInterface
import org.team9432.scoutingapp.io.ScheduleFiles
import org.team9432.scoutingapp.io.config
import org.team9432.scoutingapp.io.json.DataType
import org.team9432.scoutingapp.setAppScreen
import org.team9432.scoutingapp.ui.screens.scoutingscreens.MatchScoutingScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchSelectionScreen() {
    val matches = ScheduleFiles.getMatches()

    val dataType = if (config.isSuperscout) DataType.SUPERSCOUT else DataType.MATCH_SCOUT

    val alreadyScoutedMatches = matches.filter { MatchStorageInterface.matchDataExists(it.value, it.key, dataType) }
    val toBeScoutedMatches = matches.filterNot { alreadyScoutedMatches.contains(it.key) }

    val scrollState = LazyListState(firstVisibleItemIndex = alreadyScoutedMatches.size + 1)

    var filter by remember { mutableStateOf("") }

    fun Map<String, String>.applyFilter() = filterKeys { it.startsWith(filter) }

    Column {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().padding(10.dp),
            value = filter,
            singleLine = true,
            onValueChange = {
                filter = it
            },
            trailingIcon = { Icon(Icons.Filled.Search, null) }
        )
        LazyColumn(Modifier.fillMaxSize(), state = scrollState, horizontalAlignment = Alignment.CenterHorizontally) {
            alreadyScoutedMatches.applyFilter().forEach {
                item {
                    MatchDisplay(
                        matchNumber = it.key,
                        teamToScout = it.value,
                        dataType = dataType,
                        hasBeenScouted = true,
                        onClick = { setAppScreen(fullscreen = true) { MatchScoutingScreen(ScheduleFiles.getTeamToScout(it.key), it.key) } }
                    )
                }
            }
            item {
                Divider()
            }
            toBeScoutedMatches.applyFilter().forEach {
                item {
                    MatchDisplay(
                        matchNumber = it.key,
                        teamToScout = it.value,
                        dataType = dataType,
                        hasBeenScouted = false,
                        onClick = { setAppScreen(fullscreen = true) { MatchScoutingScreen(ScheduleFiles.getTeamToScout(it.key), it.key) } }
                    )
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MatchDisplay(matchNumber: String, teamToScout: String, dataType: DataType, onClick: () -> Unit, enabled: Boolean = true, hasBeenScouted: Boolean = false) {
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
            Row {
                if (hasBeenScouted) {
                    IconButton(onClick = { setAppScreen { QRCodeScreen(teamToScout, matchNumber, dataType) } }) {
                        Icon(Icons.Filled.QrCode2, null)
                    }
                }
                IconButton(onClick = { showMoreOptions = true }) {
                    Icon(Icons.Filled.MoreHoriz, "More Options", tint = contentColor)
                }
            }

            DropdownMenu(
                modifier = Modifier,
                expanded = showMoreOptions,
                onDismissRequest = { showMoreOptions = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Delete") },
                    onClick = { showMoreOptions = false; showDeleteDialog = true },
                    trailingIcon = { Icon(Icons.Filled.Delete, null) }
                )
                DropdownMenuItem(
                    text = { Text("QR Code") },
                    onClick = {
                        showMoreOptions = false
                        setAppScreen { QRCodeScreen(teamToScout, matchNumber, dataType) }
                    },
                    trailingIcon = { Icon(Icons.Filled.QrCode2, null) }
                )
            }
        }

    )
    if (showDeleteDialog) {
        AlertDialog(
            title = { Text(text = "Delete Data") },
            text = { Text(text = "This will permanently delete any saved data for this match") },
            onDismissRequest = { showDeleteDialog = false },
            confirmButton = { TextButton(onClick = { MatchStorageInterface.deleteMatch(teamToScout, matchNumber, dataType); showDeleteDialog = false }) { Text("Confirm") } },
            dismissButton = { TextButton(onClick = { showDeleteDialog = false }) { Text("Cancel") } }
        )
    }
}