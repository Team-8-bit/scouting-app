package org.team9432.scoutingapp.ui.screens.scoutingscreens.superscouting

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.team9432.scoutingapp.io.json.MatchScoutingDataInputs
import org.team9432.scoutingapp.io.json.SuperscoutingDataInputs
import org.team9432.scoutingapp.ui.PageChanger

@Composable
fun PreMatch(inputs: SuperscoutingDataInputs, onNext: () -> Unit, onBack: () -> Unit) {
    Column(Modifier.fillMaxSize().padding(5.dp)) {
        inputs.ScoutName(Modifier.fillMaxWidth())

        Row {
            inputs.Alliance(Modifier.fillMaxHeight().fillMaxWidth(0.5F).padding(5.dp))
            PageChanger(Modifier.fillMaxSize().padding(5.dp), onNext = onNext, onBack = onBack)
        }
    }
}