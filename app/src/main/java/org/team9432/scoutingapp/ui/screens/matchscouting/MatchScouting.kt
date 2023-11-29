package org.team9432.scoutingapp.ui.screens.matchscouting

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.team9432.scoutingapp.ui.*

private enum class Screen {
    AUTO, TELEOP, NOTES
}

private var currentScreen by mutableStateOf(Screen.AUTO)

@Composable
fun MatchScoutingScreen() {
    when (currentScreen) {
        Screen.AUTO -> Auto()
        Screen.TELEOP -> Teleop()
        Screen.NOTES -> Notes()
    }
}

@Composable
private fun Notes() {
    Column(Modifier.fillMaxSize()) {
        Row(Modifier.padding(5.dp).fillMaxHeight(0.6F).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly) {
            Column(Modifier.fillMaxHeight().width(250.dp).padding(5.dp)) {
                CycleInput(Modifier.width(250.dp).padding(5.dp), title = "Robot Role", options = listOf("N/A", "Scoring", "Defence", "Shuttle"), onChange = {})
                CycleInput(Modifier.width(250.dp).padding(5.dp), title = "Substation Preference", options = listOf("N/A", "Double", "Single", "Both"), onChange = {})
                CycleInput(Modifier.width(250.dp).fillMaxHeight().padding(5.dp), title = "GP Preference", options = listOf("N/A", "Cube", "Cone"), onChange = {})
            }
            Column(Modifier.fillMaxHeight().width(250.dp).padding(5.dp)) {
                CycleInput(Modifier.width(250.dp).padding(5.dp), title = "Defence Quality", options = listOf("N/A", "Excellent", "Acceptable", "Subpar", "Abysmal"), onChange = {})
                CycleInput(Modifier.width(250.dp).padding(5.dp), title = "Driving Quality", options = listOf("N/A", "good", "ok-ish", "not ok", "no"), onChange = {})
                CycleInput(Modifier.width(250.dp).fillMaxHeight().padding(5.dp), title = "Would Pick", options = listOf("N/A", "No", "First Pick", "Second Pick"), onChange = {})
            }
            SwitchInput(Modifier.fillMaxHeight().fillMaxWidth().padding(5.dp), title = "Disabled", onChange = {})
        }
        Row(Modifier.padding(5.dp).fillMaxHeight().fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly) {
            TextInput(Modifier.fillMaxHeight().fillMaxWidth(0.85F).padding(5.dp), title = "Notes", onChange = {})
            PageChanger(Modifier.fillMaxSize().padding(5.dp), onNext = {}, onBack = { currentScreen = Screen.TELEOP }, nextEnabled = false)
        }
    }
}

@Composable
private fun Teleop() {
    Column(Modifier.fillMaxSize()) {
        Row(Modifier.padding(5.dp).fillMaxHeight(0.33F).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly) {
            NumberInput(Modifier.fillMaxHeight().width(175.dp).padding(5.dp), title = "Tele Cones Top", onChange = {})
            NumberInput(Modifier.fillMaxHeight().width(175.dp).padding(5.dp), title = "Tele Cones Mid", onChange = {})
            NumberInput(Modifier.fillMaxHeight().width(175.dp).padding(5.dp), title = "Tele Cones Low", onChange = {})
            SwitchInput(Modifier.fillMaxHeight().fillMaxWidth(0.5F).padding(5.dp), title = "Defended", onChange = {})
            SwitchInput(Modifier.fillMaxHeight().fillMaxWidth().padding(5.dp), title = "Disabled", onChange = {})
        }
        Row(Modifier.padding(5.dp).fillMaxHeight(0.5F).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly) {
            NumberInput(Modifier.fillMaxHeight().width(175.dp).padding(5.dp), title = "Tele Cubes Top", onChange = {})
            NumberInput(Modifier.fillMaxHeight().width(175.dp).padding(5.dp), title = "Tele Cubes Mid", onChange = {})
            NumberInput(Modifier.fillMaxHeight().width(175.dp).padding(5.dp), title = "Tele Cubes Low", onChange = {})
            NumberInput(Modifier.fillMaxHeight().width(175.dp).padding(5.dp), title = "Penalties", onChange = {})
            CycleInput(Modifier.fillMaxHeight().fillMaxWidth().padding(5.dp), title = "Tele Engage", options = listOf("No attempt", "Success", "Fail", "Docked"), onChange = {})
        }
        Row(Modifier.padding(5.dp).fillMaxHeight().fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly) {
            NumberInput(Modifier.fillMaxHeight().width(175.dp).padding(5.dp), title = "Cones Dropped", onChange = {})
            NumberInput(Modifier.fillMaxHeight().width(175.dp).padding(5.dp), title = "Cubes Dropped", onChange = {})
            NumberInput(Modifier.fillMaxHeight().width(175.dp).padding(5.dp), title = "Cones Missed", onChange = {})
            NumberInput(Modifier.fillMaxHeight().width(175.dp).padding(5.dp), title = "Cubes Missed", onChange = {})
            PageChanger(Modifier.fillMaxSize().padding(5.dp), onNext = { currentScreen = Screen.NOTES }, onBack = { currentScreen = Screen.AUTO })
        }
    }
}

@Composable
private fun Auto() {
    Column(Modifier.fillMaxSize()) {
        Row(Modifier.padding(5.dp).fillMaxHeight(0.5F).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly) {
            SwitchInput(Modifier.fillMaxHeight().width(100.dp).padding(5.dp), title = "Mobility", onChange = {})
            NumberInput(Modifier.fillMaxHeight().width(200.dp).padding(5.dp), title = "Auto Cones Top", onChange = {})
            NumberInput(Modifier.fillMaxHeight().width(200.dp).padding(5.dp), title = "Auto Cones Mid", onChange = {})
            NumberInput(Modifier.fillMaxHeight().width(200.dp).padding(5.dp), title = "Auto Cones Low", onChange = {})
            BlankInput(Modifier.fillMaxHeight().fillMaxWidth().padding(5.dp))
        }
        Row(Modifier.padding(5.dp).fillMaxHeight().fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly) {
            CycleInput(Modifier.fillMaxHeight().width(150.dp).padding(5.dp), title = "Auto Engage", options = listOf("No attempt", "Success", "Fail", "Docked"), onChange = {})
            NumberInput(Modifier.fillMaxHeight().width(200.dp).padding(5.dp), title = "Auto Cubes Top", onChange = {})
            NumberInput(Modifier.fillMaxHeight().width(200.dp).padding(5.dp), title = "Auto Cubes Mid", onChange = {})
            NumberInput(Modifier.fillMaxHeight().width(200.dp).padding(5.dp), title = "Auto Cubes Low", onChange = {})
            PageChanger(Modifier.fillMaxSize().padding(5.dp), onNext = { currentScreen = Screen.TELEOP }, onBack = {}, backEnabled = false)
        }
    }
}

