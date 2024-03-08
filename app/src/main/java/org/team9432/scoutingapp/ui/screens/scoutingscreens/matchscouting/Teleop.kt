package org.team9432.scoutingapp.ui.screens.scoutingscreens.matchscouting

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.team9432.scoutingapp.io.json.MatchScoutingDataInputs
import org.team9432.scoutingapp.ui.PageChanger


@Composable
fun Teleop(inputs: MatchScoutingDataInputs, onNext: () -> Unit, onBack: () -> Unit) {
    Row(Modifier.fillMaxSize()) {
        Column(Modifier.padding(5.dp).fillMaxHeight().fillMaxWidth(0.25F), verticalArrangement = Arrangement.SpaceEvenly, horizontalAlignment = Alignment.CenterHorizontally) {
            inputs.TeleAmpNotes(Modifier.fillMaxWidth().fillMaxHeight(0.5F))
            inputs.TeleAmpNotesMissed(Modifier.fillMaxWidth().fillMaxHeight())
        }
        Column(Modifier.padding(5.dp).fillMaxHeight().fillMaxWidth(0.33F), verticalArrangement = Arrangement.SpaceEvenly, horizontalAlignment = Alignment.CenterHorizontally) {
            inputs.TeleSpeakerNotes(Modifier.fillMaxWidth().fillMaxHeight(0.5F))
            inputs.TeleSpeakerNotesMissed(Modifier.fillMaxWidth().fillMaxHeight())
        }
        Column(Modifier.padding(5.dp).fillMaxHeight().fillMaxWidth(0.5F), verticalArrangement = Arrangement.SpaceEvenly, horizontalAlignment = Alignment.CenterHorizontally) {
            inputs.Endgame(Modifier.fillMaxWidth().fillMaxHeight(0.5F))
            inputs.ScoredTrap(Modifier.fillMaxWidth().fillMaxHeight())
        }
        Column(Modifier.padding(5.dp).fillMaxHeight().fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceEvenly) {
            PageChanger(Modifier.fillMaxSize().padding(5.dp), onNext = onNext, onBack = onBack)
        }
    }
}