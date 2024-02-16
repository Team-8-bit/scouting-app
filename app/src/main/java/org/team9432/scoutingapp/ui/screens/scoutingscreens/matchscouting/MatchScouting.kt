package org.team9432.scoutingapp.ui.screens.scoutingscreens.matchscouting

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import org.team9432.scoutingapp.io.MatchStorageInterface
import org.team9432.scoutingapp.io.config
import org.team9432.scoutingapp.io.json.*
import org.team9432.scoutingapp.setAppScreen
import org.team9432.scoutingapp.ui.screens.MatchSelectionScreen
import org.team9432.scoutingapp.ui.screens.QRCodeScreen
import org.team9432.scoutingapp.ui.screens.scoutingscreens.Dialog

private enum class Screen {
    PRE_MATCH, AUTO, TELEOP, NOTES
}

@Composable
fun MatchScouting(teamToScout: String, matchNumber: String) {
    var saveDialogOpen by remember { mutableStateOf(false) }
    var exitDialogOpen by remember { mutableStateOf(false) }

    var currentScreen by remember { mutableStateOf(Screen.PRE_MATCH) }
    var matchData by remember { mutableStateOf(MatchStorageInterface.getMatchDataOrNew(teamToScout, matchNumber)) }
    fun changeScreen(screen: Screen) {
        currentScreen = screen
    }

    val updateData = { updateData: (MatchScoutingData) -> MatchScoutingData -> matchData = updateData(matchData) }

    val inputs by remember {
        mutableStateOf(
            MatchScoutingDataInputs(
                updateData = updateData,
                initialData = matchData,
                getCurrentData = { matchData },
                defaultModifier = { this.padding(5.dp) },
            )
        )
    }

    when (currentScreen) {
        Screen.PRE_MATCH -> PreMatch(inputs, { changeScreen(Screen.AUTO) }, { exitDialogOpen = true })
        Screen.AUTO -> Auto(inputs, { changeScreen(Screen.TELEOP) }, { changeScreen(Screen.PRE_MATCH) })
        Screen.TELEOP -> Teleop(inputs, { currentScreen = Screen.NOTES }, { currentScreen = Screen.AUTO })
        Screen.NOTES -> Notes(inputs, { saveDialogOpen = true }, { changeScreen(Screen.TELEOP) })
    }

    if (saveDialogOpen) {
        Dialog(
            title = "Save Data",
            body = "This will overwrite any saved data for this match",
            onAgree = {
                val data = matchData
                val metadata = MatchMetadata(
                    DataType.MATCH_SCOUT,
                    matchNumber,
                    teamToScout,
                    config.scoutID.toString()
                )
                MatchStorageInterface.writeData(MatchData(metadata, data))
                val dataType = if (config.isSuperscout) DataType.SUPERSCOUT else DataType.MATCH_SCOUT
                setAppScreen { QRCodeScreen(teamToScout, matchNumber, dataType) }
            },
            onDismiss = { saveDialogOpen = false }
        )
    }
    if (exitDialogOpen) {
        Dialog(
            title = "Exit Match",
            body = "This will not save any input data",
            onAgree = { setAppScreen { MatchSelectionScreen() } },
            onDismiss = { exitDialogOpen = false }
        )
    }
}