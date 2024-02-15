package org.team9432.scoutingapp.ui.screens.scoutingscreens.matchscouting

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import org.team9432.scoutingapp.io.MatchScoutingFile
import org.team9432.scoutingapp.io.json.MatchScoutingData
import org.team9432.scoutingapp.io.json.MatchScoutingDataInputs
import org.team9432.scoutingapp.setAppScreen
import org.team9432.scoutingapp.ui.screens.MatchSelectionScreen
import org.team9432.scoutingapp.ui.screens.QRCodeScreen
import org.team9432.scoutingapp.ui.screens.scoutingscreens.Dialog

private enum class Screen {
    PRE_MATCH, AUTO, TELEOP, NOTES
}

@Composable
fun MatchScouting(teamToScout: String, matchNumber: Int) {
    var saveDialogOpen by remember { mutableStateOf(false) }
    var exitDialogOpen by remember { mutableStateOf(false) }

    var currentScreen by remember { mutableStateOf(Screen.PRE_MATCH) }
    var matchData by remember { mutableStateOf(MatchScoutingFile.data.getMatchOrNew(matchNumber, teamToScout)) }
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
                MatchScoutingFile.addMatchData(matchData)
                setAppScreen { QRCodeScreen(teamToScout, matchNumber) }
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