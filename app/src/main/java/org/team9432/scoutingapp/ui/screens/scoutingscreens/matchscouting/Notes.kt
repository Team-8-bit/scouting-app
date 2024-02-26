package org.team9432.scoutingapp.ui.screens.scoutingscreens.matchscouting

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.team9432.scoutingapp.io.json.MatchScoutingDataInputs
import org.team9432.scoutingapp.ui.PageChanger
import org.team9432.scoutingapp.ui.SubmitButton

@Composable
fun Notes(inputs: MatchScoutingDataInputs, onNext: () -> Unit, onBack: () -> Unit) {
    Column(Modifier.fillMaxSize()) {
        Row(Modifier.padding(5.dp).fillMaxHeight(0.5F).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly) {
            inputs.Disabled(Modifier.fillMaxHeight().fillMaxWidth(0.33F))
            SubmitButton(Modifier.fillMaxHeight().fillMaxWidth(0.5F).padding(5.dp), onPressed = onNext)
            PageChanger(Modifier.fillMaxSize().padding(5.dp), onNext = {}, onBack = onBack, nextEnabled = false)
        }
        inputs.Notes(Modifier.fillMaxHeight().fillMaxWidth())
    }
}