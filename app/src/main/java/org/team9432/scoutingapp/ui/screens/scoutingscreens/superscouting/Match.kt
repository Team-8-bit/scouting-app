package org.team9432.scoutingapp.ui.screens.scoutingscreens.superscouting

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.team9432.scoutingapp.io.json.SuperscoutingDataInputs
import org.team9432.scoutingapp.ui.PageChanger


@Composable
fun Match(inputs: SuperscoutingDataInputs, onNext: () -> Unit, onBack: () -> Unit) {
    Row(Modifier.fillMaxSize()) {
        Column(Modifier.padding(5.dp).fillMaxHeight().fillMaxWidth(0.33F), verticalArrangement = Arrangement.SpaceEvenly, horizontalAlignment = Alignment.CenterHorizontally) {
            inputs.Cycles(Modifier.fillMaxWidth().fillMaxHeight(0.5F))
            Column(Modifier.fillMaxWidth().fillMaxHeight()) {
                inputs.PrimaryScoringLocation(Modifier.fillMaxWidth().fillMaxHeight(0.5F))
                inputs.PrimaryPickupLocation(Modifier.fillMaxWidth().fillMaxHeight())
            }
        }
        Column(Modifier.padding(5.dp).fillMaxHeight().fillMaxWidth(0.5F), verticalArrangement = Arrangement.SpaceEvenly, horizontalAlignment = Alignment.CenterHorizontally) {
            inputs.AmplifierActivated(Modifier.fillMaxWidth().fillMaxHeight(0.5F))
            inputs.CoopActivated(Modifier.fillMaxWidth().fillMaxHeight(), title = "Co-Op Activated")
        }
        Column(Modifier.padding(5.dp).fillMaxHeight().fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceEvenly) {
            inputs.Penalties(Modifier.fillMaxHeight(0.5F).fillMaxWidth())
            PageChanger(Modifier.fillMaxSize().padding(5.dp), onNext = onNext, onBack = onBack)
        }
    }
}