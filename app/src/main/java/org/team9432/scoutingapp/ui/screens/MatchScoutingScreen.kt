package org.team9432.scoutingapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.team9432.scoutingapp.currentScreenType
import org.team9432.scoutingapp.io.MatchScoutingFile
import org.team9432.scoutingapp.io.json.MatchScoutingData
import org.team9432.scoutingapp.io.json.MatchScoutingDataInputs
import org.team9432.scoutingapp.ui.BlankInput
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
            confirmButton = {
                TextButton(onClick = {
                    MatchScoutingFile.addMatchData(matchData)
                    org.team9432.scoutingapp.currentScreen = { MatchSelectionScreen() }
                    currentScreenType = org.team9432.scoutingapp.Screen.MATCH_SELECTION
                }) { Text("Confirm") }
            },
            dismissButton = { TextButton(onClick = { saveDialogOpen = false }) { Text("Cancel") } }
        )
    }
}

@Composable
private fun Notes(inputs: MatchScoutingDataInputs, setScreen: (Screen) -> Unit, onSave: () -> Unit) {
    Column(Modifier.fillMaxSize()) {
        Row(Modifier.padding(5.dp).fillMaxHeight(0.6F).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly) {
            Column(Modifier.fillMaxHeight().width(250.dp).padding(5.dp)) {
                inputs.RobotRole(Modifier.width(250.dp))
                inputs.SubstationPreference(Modifier.width(250.dp))
                inputs.GamePiecePreference(Modifier.width(250.dp), title = "GP Preference")
            }
            Column(Modifier.fillMaxHeight().width(250.dp).padding(5.dp)) {
                inputs.DefenceQuality(Modifier.width(250.dp))
                inputs.DrivingQuality(Modifier.width(250.dp))
                inputs.WouldPick(Modifier.width(250.dp))
            }
            BlankInput(Modifier.fillMaxHeight().fillMaxWidth(0.5F).padding(5.dp))
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
    Column(Modifier.fillMaxSize()) {
        Row(Modifier.padding(5.dp).fillMaxHeight(0.33F).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly) {
            inputs.TeleConesTop(Modifier.fillMaxHeight().width(175.dp))
            inputs.TeleConesMid(Modifier.fillMaxHeight().width(175.dp))
            inputs.TeleConesLow(Modifier.fillMaxHeight().width(175.dp))
            inputs.Defended(Modifier.fillMaxHeight().fillMaxWidth(0.5F))
            inputs.Disabled(Modifier.fillMaxHeight().fillMaxWidth())
        }
        Row(Modifier.padding(5.dp).fillMaxHeight(0.5F).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly) {
            inputs.TeleCubesTop(Modifier.fillMaxHeight().width(175.dp))
            inputs.TeleCubesMid(Modifier.fillMaxHeight().width(175.dp))
            inputs.TeleCubesLow(Modifier.fillMaxHeight().width(175.dp))
            inputs.Penalties(Modifier.fillMaxHeight().width(175.dp))
            inputs.TeleEngage(Modifier.fillMaxHeight().fillMaxWidth())
        }
        Row(Modifier.padding(5.dp).fillMaxHeight().fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly) {
            inputs.ConesDropped(Modifier.fillMaxHeight().width(175.dp))
            inputs.CubesDropped(Modifier.fillMaxHeight().width(175.dp))
            inputs.ConesMissed(Modifier.fillMaxHeight().width(175.dp))
            inputs.CubesMissed(Modifier.fillMaxHeight().width(175.dp))
            PageChanger(Modifier.fillMaxSize().padding(5.dp), onNext = { setScreen(Screen.NOTES) }, onBack = { setScreen(Screen.AUTO) })
        }
    }
}

@Composable
private fun Auto(inputs: MatchScoutingDataInputs, setScreen: (Screen) -> Unit) {
    Column(Modifier.fillMaxSize()) {
        Row(Modifier.padding(5.dp).fillMaxHeight(0.5F).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly) {
            inputs.AutoMobility(Modifier.fillMaxHeight().width(100.dp))
            inputs.AutoConesTop(Modifier.fillMaxHeight().width(200.dp))
            inputs.AutoConesMid(Modifier.fillMaxHeight().width(200.dp))
            inputs.AutoConesLow(Modifier.fillMaxHeight().width(200.dp))
            BlankInput(Modifier.fillMaxHeight().fillMaxWidth().padding(5.dp))
        }
        Row(Modifier.padding(5.dp).fillMaxHeight().fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly) {
            inputs.AutoEngage(Modifier.fillMaxHeight().width(150.dp))
            inputs.AutoCubesTop(Modifier.fillMaxHeight().width(200.dp))
            inputs.AutoCubesMid(Modifier.fillMaxHeight().width(200.dp))
            inputs.AutoCubesLow(Modifier.fillMaxHeight().width(200.dp))
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
            BlankInput(Modifier.fillMaxHeight().fillMaxWidth(0.5F).padding(5.dp))
            Column(Modifier.fillMaxSize()) {
                BlankInput(Modifier.fillMaxWidth().fillMaxHeight(0.5F).padding(5.dp))
                PageChanger(Modifier.fillMaxSize().padding(5.dp), onNext = { setScreen(Screen.AUTO) }, onBack = {}, backEnabled = false)
            }
        }
    }
}