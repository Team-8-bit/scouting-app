package org.team9432.scoutingapp.ui.screens.matchscouting

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import org.team9432.scoutingapp.appScreen
import org.team9432.scoutingapp.io.MatchScoutFile
import org.team9432.scoutingapp.io.data.ChargedUpMatchScoutingData
import org.team9432.scoutingapp.io.data.MatchScoutingMatchData
import org.team9432.scoutingapp.io.data.ScheduledMatch
import org.team9432.scoutingapp.ui.*

private enum class Screen {
    PRE_MATCH, AUTO, TELEOP, NOTES
}

@Composable
fun MatchScoutingScreen(match: ScheduledMatch, scoutID: Int) {
    var currentScreen by remember { mutableStateOf(Screen.PRE_MATCH) }
    var data by remember { mutableStateOf(MatchScoutingMatchData(match.number, match.teams[scoutID]!!.teamNumber, scoutID, ChargedUpMatchScoutingData())) }

    val updateScreen = { it: Screen -> currentScreen = it }
    val updateMatchScoutingData = { updateData: (ChargedUpMatchScoutingData) -> ChargedUpMatchScoutingData -> data = data.copy(data = updateData(data.data)) }
    val updateData = { updateData: (MatchScoutingMatchData) -> MatchScoutingMatchData -> data = updateData(data) }
    when (currentScreen) {
        Screen.PRE_MATCH -> PreMatch(updateScreen, updateData, match, scoutID)
        Screen.AUTO -> Auto(updateScreen, updateMatchScoutingData)
        Screen.TELEOP -> Teleop(updateScreen, updateMatchScoutingData)
        Screen.NOTES -> Notes(updateScreen, updateMatchScoutingData, onSave = { MatchScoutFile.addTeamToMatch(data.team, data); appScreen = org.team9432.scoutingapp.Screen.MATCH_SELECTION })
    }
}

@Composable
private fun Notes(setScreen: (Screen) -> Unit, updateData: ((ChargedUpMatchScoutingData) -> ChargedUpMatchScoutingData) -> Unit, onSave: () -> Unit) {
    Column(Modifier.fillMaxSize()) {
        Row(Modifier.padding(5.dp).fillMaxHeight(0.6F).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly) {
            Column(Modifier.fillMaxHeight().width(250.dp).padding(5.dp)) {
                CycleInput(
                    Modifier.width(250.dp).padding(5.dp),
                    title = "Robot Role",
                    options = listOf("N/A", "Scoring", "Defence", "Shuttle"),
                    onChange = { newValue -> updateData { it.copy(robotRole = newValue) } })
                CycleInput(
                    Modifier.width(250.dp).padding(5.dp),
                    title = "Substation Preference",
                    options = listOf("N/A", "Double", "Single", "Both"),
                    onChange = { newValue -> updateData { it.copy(substationPreference = newValue) } })
                CycleInput(
                    Modifier.width(250.dp).fillMaxHeight().padding(5.dp),
                    title = "GP Preference",
                    options = listOf("N/A", "Cube", "Cone"),
                    onChange = { newValue -> updateData { it.copy(gamePiecePreference = newValue) } })
            }
            Column(Modifier.fillMaxHeight().width(250.dp).padding(5.dp)) {
                CycleInput(
                    Modifier.width(250.dp).padding(5.dp),
                    title = "Defence Quality",
                    options = listOf("N/A", "Excellent", "Acceptable", "Subpar", "Abysmal"),
                    onChange = { newValue -> updateData { it.copy(defenceQuality = newValue) } })
                CycleInput(
                    Modifier.width(250.dp).padding(5.dp),
                    title = "Driving Quality",
                    options = listOf("N/A", "good", "ok-ish", "not ok", "no"),
                    onChange = { newValue -> updateData { it.copy(drivingQuality = newValue) } })
                CycleInput(
                    Modifier.width(250.dp).fillMaxHeight().padding(5.dp),
                    title = "Would Pick",
                    options = listOf("N/A", "No", "First Pick", "Second Pick"),
                    onChange = { newValue -> updateData { it.copy(wouldPick = newValue) } })
            }
            BlankInput(Modifier.fillMaxHeight().fillMaxWidth(0.5F).padding(5.dp))
            SubmitButton(Modifier.fillMaxSize().padding(5.dp), onPressed = onSave)
        }
        Row(Modifier.padding(5.dp).fillMaxHeight().fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly) {
            TextInput(Modifier.fillMaxHeight().fillMaxWidth(0.85F).padding(5.dp), title = "Notes", onChange = { newValue -> updateData { it.copy(notes = newValue) } })
            PageChanger(Modifier.fillMaxSize().padding(5.dp), onNext = {}, onBack = { setScreen(Screen.TELEOP) }, nextEnabled = false)
        }
    }
}

@Composable
private fun Teleop(setScreen: (Screen) -> Unit, updateData: ((ChargedUpMatchScoutingData) -> ChargedUpMatchScoutingData) -> Unit) {
    Column(Modifier.fillMaxSize()) {
        Row(Modifier.padding(5.dp).fillMaxHeight(0.33F).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly) {
            NumberInput(Modifier.fillMaxHeight().width(175.dp).padding(5.dp), title = "Tele Cones Top", onChange = { newValue -> updateData { it.copy(teleConesTop = newValue) } })
            NumberInput(Modifier.fillMaxHeight().width(175.dp).padding(5.dp), title = "Tele Cones Mid", onChange = { newValue -> updateData { it.copy(teleConesMid = newValue) } })
            NumberInput(Modifier.fillMaxHeight().width(175.dp).padding(5.dp), title = "Tele Cones Low", onChange = { newValue -> updateData { it.copy(teleConesLow = newValue) } })
            SwitchInput(Modifier.fillMaxHeight().fillMaxWidth(0.5F).padding(5.dp), title = "Defended", onChange = { newValue -> updateData { it.copy(defended = newValue) } })
            SwitchInput(Modifier.fillMaxHeight().fillMaxWidth().padding(5.dp), title = "Disabled", onChange = { newValue -> updateData { it.copy(disabled = newValue) } })
        }
        Row(Modifier.padding(5.dp).fillMaxHeight(0.5F).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly) {
            NumberInput(Modifier.fillMaxHeight().width(175.dp).padding(5.dp), title = "Tele Cubes Top", onChange = { newValue -> updateData { it.copy(teleCubesTop = newValue) } })
            NumberInput(Modifier.fillMaxHeight().width(175.dp).padding(5.dp), title = "Tele Cubes Mid", onChange = { newValue -> updateData { it.copy(teleCubesMid = newValue) } })
            NumberInput(Modifier.fillMaxHeight().width(175.dp).padding(5.dp), title = "Tele Cubes Low", onChange = { newValue -> updateData { it.copy(teleCubesLow = newValue) } })
            NumberInput(Modifier.fillMaxHeight().width(175.dp).padding(5.dp), title = "Penalties", onChange = { newValue -> updateData { it.copy(penalties = newValue) } })
            CycleInput(
                Modifier.fillMaxHeight().fillMaxWidth().padding(5.dp),
                title = "Tele Engage",
                options = listOf("No attempt", "Success", "Fail", "Docked"),
                onChange = { newValue -> updateData { it.copy(teleEngage = newValue) } })
        }
        Row(Modifier.padding(5.dp).fillMaxHeight().fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly) {
            NumberInput(Modifier.fillMaxHeight().width(175.dp).padding(5.dp), title = "Cones Dropped", onChange = { newValue -> updateData { it.copy(conesDropped = newValue) } })
            NumberInput(Modifier.fillMaxHeight().width(175.dp).padding(5.dp), title = "Cubes Dropped", onChange = { newValue -> updateData { it.copy(cubesDropped = newValue) } })
            NumberInput(Modifier.fillMaxHeight().width(175.dp).padding(5.dp), title = "Cones Missed", onChange = { newValue -> updateData { it.copy(conesMissed = newValue) } })
            NumberInput(Modifier.fillMaxHeight().width(175.dp).padding(5.dp), title = "Cubes Missed", onChange = { newValue -> updateData { it.copy(cubesMissed = newValue) } })
            PageChanger(Modifier.fillMaxSize().padding(5.dp), onNext = { setScreen(Screen.NOTES) }, onBack = { setScreen(Screen.AUTO) })
        }
    }
}

@Composable
private fun Auto(setScreen: (Screen) -> Unit, updateData: ((ChargedUpMatchScoutingData) -> ChargedUpMatchScoutingData) -> Unit) {
    Column(Modifier.fillMaxSize()) {
        Row(Modifier.padding(5.dp).fillMaxHeight(0.5F).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly) {
            SwitchInput(Modifier.fillMaxHeight().width(100.dp).padding(5.dp), title = "Mobility", onChange = { newValue -> updateData { it.copy(autoMobility = newValue) } })
            NumberInput(Modifier.fillMaxHeight().width(200.dp).padding(5.dp), title = "Auto Cones Top", onChange = { newValue -> updateData { it.copy(autoConesTop = newValue) } })
            NumberInput(Modifier.fillMaxHeight().width(200.dp).padding(5.dp), title = "Auto Cones Mid", onChange = { newValue -> updateData { it.copy(autoConesMid = newValue) } })
            NumberInput(Modifier.fillMaxHeight().width(200.dp).padding(5.dp), title = "Auto Cones Low", onChange = { newValue -> updateData { it.copy(autoConesLow = newValue) } })
            BlankInput(Modifier.fillMaxHeight().fillMaxWidth().padding(5.dp))
        }
        Row(Modifier.padding(5.dp).fillMaxHeight().fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly) {
            CycleInput(Modifier.fillMaxHeight().width(150.dp).padding(5.dp), title = "Auto Engage", options = listOf("No attempt", "Success", "Fail", "Docked"), onChange = {})
            NumberInput(Modifier.fillMaxHeight().width(200.dp).padding(5.dp), title = "Auto Cubes Top", onChange = { newValue -> updateData { it.copy(autoCubesTop = newValue) } })
            NumberInput(Modifier.fillMaxHeight().width(200.dp).padding(5.dp), title = "Auto Cubes Mid", onChange = { newValue -> updateData { it.copy(autoCubesMid = newValue) } })
            NumberInput(Modifier.fillMaxHeight().width(200.dp).padding(5.dp), title = "Auto Cubes Low", onChange = { newValue -> updateData { it.copy(autoCubesLow = newValue) } })
            PageChanger(Modifier.fillMaxSize().padding(5.dp), onNext = { setScreen(Screen.TELEOP) }, onBack = { setScreen(Screen.PRE_MATCH) })
        }
    }
}

@Composable
private fun PreMatch(setScreen: (Screen) -> Unit, updateData: ((MatchScoutingMatchData) -> MatchScoutingMatchData) -> Unit, match: ScheduledMatch, scoutID: Int) {
    Column(Modifier.fillMaxSize()) {
        InlineTextInput(
            Modifier.fillMaxWidth().padding(5.dp),
            title = "Match Number:",
            initialValue = match.number.toString(),
            onChange = { newMatchNumber -> updateData { it.copy(matchNumber = newMatchNumber.toInt()) } },
            predicate = { it.isDigitsOnly() && it.isNotBlank() }
        )
        InlineTextInput(
            Modifier.fillMaxWidth().padding(5.dp),
            title = "Scout ID:",
            initialValue = scoutID.toString(),
            onChange = { newScoutID -> updateData { it.copy(scoutID = newScoutID.toInt()) } },
            predicate = { it.isDigitsOnly() && it.isNotBlank() }
        )
        InlineTextInput(
            Modifier.fillMaxWidth().padding(5.dp),
            title = "Team to Scout:",
            initialValue = match.teams[scoutID]?.teamNumber.toString(),
            onChange = { newTeam -> updateData { it.copy(team = newTeam) } },
            predicate = { it.isDigitsOnly() && it.isNotBlank() }
        )
        PageChanger(
            Modifier.fillMaxSize().padding(5.dp),
            onNext = { setScreen(Screen.AUTO) },
            onBack = {},
            backEnabled = false
        )
    }
}