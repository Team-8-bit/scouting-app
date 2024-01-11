package org.team9432.scoutingapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.team9432.scoutingapp.appScreen
import org.team9432.scoutingapp.io.MatchScoutingFile
import org.team9432.scoutingapp.io.json.MatchScoutingData
import org.team9432.scoutingapp.io.json.MatchScoutingDataInputs
import org.team9432.scoutingapp.ui.PageChanger
import org.team9432.scoutingapp.ui.SubmitButton

private enum class Screen {
    PRE_MATCH, AUTO, TELEOP, NOTES
}

@Composable
fun MatchScoutingScreen(teamToScout: String, matchNumber: Int) {
    var currentScreen by remember { mutableStateOf(Screen.PRE_MATCH) }
    var saveDialogOpen by remember { mutableStateOf(false) }

    var matchData by remember { mutableStateOf(MatchScoutingFile.data.getMatchOrNew(matchNumber, teamToScout)) }
    val changeScreen = { screen: Screen -> currentScreen = screen }
    val updateData = { updateData: (MatchScoutingData) -> MatchScoutingData -> matchData = updateData(matchData) }

    val inputs by remember {
        mutableStateOf(
            MatchScoutingDataInputs(
                updateData = updateData,
                initialData = matchData,
                defaultModifier = { this.padding(5.dp) },
            )
        )
    }

    when (currentScreen) {
        Screen.PRE_MATCH -> PreMatch(inputs, changeScreen)
        Screen.AUTO -> Auto(inputs, changeScreen)
        Screen.TELEOP -> Teleop(inputs, changeScreen)
        Screen.NOTES -> Notes(inputs, changeScreen) { saveDialogOpen = true }
    }

    if (saveDialogOpen) {
        AlertDialog(
            title = { Text(text = "Save Data") },
            text = { Text(text = "This will overwrite any saved data for this match") },
            onDismissRequest = { saveDialogOpen = false },
            confirmButton = { TextButton(onClick = { MatchScoutingFile.addMatchData(matchData); appScreen = org.team9432.scoutingapp.Screen.MATCH_SELECTION }) { Text("Confirm") } },
            dismissButton = { TextButton(onClick = { saveDialogOpen = false }) { Text("Cancel") } }
        )
    }
}

@Composable
private fun Notes(inputs: MatchScoutingDataInputs, setScreen: (Screen) -> Unit, onSave: () -> Unit) {
    Column(Modifier.fillMaxSize()) {
        Row(Modifier.padding(5.dp).fillMaxHeight(0.6F).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly) {
            inputs.Penalties(Modifier.fillMaxHeight().fillMaxWidth(0.2F))
            inputs.DefenceQuality(Modifier.fillMaxHeight().fillMaxWidth(0.25F))
            inputs.DrivingQuality(Modifier.fillMaxHeight().fillMaxWidth(0.33F))
            inputs.Disabled(Modifier.fillMaxHeight().fillMaxWidth(0.5F))
            SubmitButton(Modifier.fillMaxSize().padding(5.dp), onPressed = onSave)
        }
        Row(Modifier.padding(5.dp).fillMaxHeight().fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly) {
            inputs.Notes(Modifier.fillMaxHeight().fillMaxWidth(0.85F))
            PageChanger(Modifier.fillMaxSize().padding(5.dp), onNext = {}, onBack = { setScreen(Screen.TELEOP) }, nextEnabled = false)
        }
    }
}

@Composable
private fun Teleop(inputs: MatchScoutingDataInputs, setScreen: (Screen) -> Unit) {
    Row(Modifier.fillMaxSize()) {
        Column(Modifier.padding(5.dp).fillMaxHeight().fillMaxWidth(0.25F), verticalArrangement = Arrangement.SpaceEvenly, horizontalAlignment = Alignment.CenterHorizontally) {
            inputs.TeleAmpNotes(Modifier.fillMaxWidth().fillMaxHeight(0.5F))
            inputs.TeleAmpNotesMissed(Modifier.fillMaxWidth().fillMaxHeight())
        }
        Column(Modifier.padding(5.dp).fillMaxHeight().fillMaxWidth(0.33F), verticalArrangement = Arrangement.SpaceEvenly, horizontalAlignment = Alignment.CenterHorizontally) {
            inputs.TeleSpeakerNotesMissed(Modifier.fillMaxWidth().fillMaxHeight(0.5F))
            inputs.TeleSpeakerNotes(Modifier.fillMaxWidth().fillMaxHeight())
        }
        Column(Modifier.padding(5.dp).fillMaxHeight().fillMaxWidth(0.5F), verticalArrangement = Arrangement.SpaceEvenly, horizontalAlignment = Alignment.CenterHorizontally) {
            inputs.Endgame(Modifier.fillMaxWidth().fillMaxHeight(0.5F))
            inputs.ScoredTrap(Modifier.fillMaxWidth().fillMaxHeight())
        }
        Column(Modifier.padding(5.dp).fillMaxHeight().fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceEvenly) {
            inputs.Penalties(Modifier.fillMaxHeight(0.5F).fillMaxWidth())
            PageChanger(Modifier.fillMaxSize().padding(5.dp), onNext = { setScreen(Screen.NOTES) }, onBack = { setScreen(Screen.AUTO) })
        }
    }
}

@Composable
private fun Auto(inputs: MatchScoutingDataInputs, setScreen: (Screen) -> Unit) {
    Row(Modifier.fillMaxSize()) {
        Column(Modifier.padding(5.dp).fillMaxHeight().fillMaxWidth(0.33F), verticalArrangement = Arrangement.SpaceEvenly, horizontalAlignment = Alignment.CenterHorizontally) {
            inputs.AutoScoredSpeaker(Modifier.fillMaxHeight(0.5F).fillMaxWidth())
            inputs.AutoScoredAmp(Modifier.fillMaxHeight().fillMaxWidth())
        }
        Column(Modifier.padding(5.dp).fillMaxHeight().fillMaxWidth(0.5F), verticalArrangement = Arrangement.SpaceEvenly, horizontalAlignment = Alignment.CenterHorizontally) {
            inputs.PickupAutoNoteOne(Modifier.fillMaxHeight(0.2F).fillMaxWidth())
            inputs.PickupAutoNoteTwo(Modifier.fillMaxHeight(0.25F).fillMaxWidth())
            inputs.PickupAutoNoteThree(Modifier.fillMaxHeight(0.33F).fillMaxWidth())
            inputs.PickupAutoNoteFour(Modifier.fillMaxHeight(0.5F).fillMaxWidth())
            inputs.PickupAutoNoteFive(Modifier.fillMaxHeight().fillMaxWidth())
        }
        Column(Modifier.padding(5.dp).fillMaxHeight().fillMaxWidth(), verticalArrangement = Arrangement.SpaceEvenly, horizontalAlignment = Alignment.CenterHorizontally) {
            inputs.AutoStartingPosition(Modifier.fillMaxHeight(0.33F).fillMaxWidth())
            inputs.CrossedAutoLine(Modifier.fillMaxHeight(0.5F).fillMaxWidth())
            PageChanger(Modifier.fillMaxSize().padding(5.dp), onNext = { setScreen(Screen.TELEOP) }, onBack = { setScreen(Screen.PRE_MATCH) })
        }
    }
}

@Composable
private fun PreMatch(inputs: MatchScoutingDataInputs, setScreen: (Screen) -> Unit) {
    Column(Modifier.fillMaxSize().padding(5.dp)) {
        inputs.MatchNumber(Modifier.fillMaxWidth())
        inputs.ScoutID(Modifier.fillMaxWidth(), title = "Scout ID")
        inputs.TeamNumber(Modifier.fillMaxWidth(), title = "Team to Scout")

        Row {
            inputs.Alliance(Modifier.fillMaxHeight().fillMaxWidth(0.5F).padding(5.dp))
            PageChanger(Modifier.fillMaxSize().padding(5.dp), onNext = { setScreen(Screen.AUTO) }, onBack = {}, backEnabled = false)
        }
    }
}