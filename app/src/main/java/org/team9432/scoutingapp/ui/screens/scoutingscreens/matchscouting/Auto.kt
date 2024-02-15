package org.team9432.scoutingapp.ui.screens.scoutingscreens.matchscouting

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.team9432.scoutingapp.io.json.MatchScoutingDataInputs
import org.team9432.scoutingapp.ui.PageChanger

@Composable
fun Auto(inputs: MatchScoutingDataInputs, onNext: () -> Unit, onBack: () -> Unit) {
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
            PageChanger(Modifier.fillMaxSize().padding(5.dp), onNext = onNext, onBack = onBack)
        }
    }
}