package org.team9432.scoutingapp.ui.screens.scoutingscreens.superscouting

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.team9432.scoutingapp.io.json.SuperscoutingDataInputs
import org.team9432.scoutingapp.ui.PageChanger
import org.team9432.scoutingapp.ui.SubmitButton

@Composable
fun Notes(inputs: SuperscoutingDataInputs, onNext: () -> Unit, onBack: () -> Unit) {
    Column(Modifier.fillMaxSize()) {
        Row(Modifier.padding(5.dp).fillMaxHeight(0.5F).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly) {
            inputs.DrivingQuality(Modifier.fillMaxHeight().fillMaxWidth(0.25F), title = "Driving:\n1 = Worst - 3 = Best")
            inputs.RobotRole(Modifier.fillMaxHeight().fillMaxWidth(0.33F))
            inputs.WasDefended(Modifier.fillMaxHeight().fillMaxWidth(0.5F))
            SubmitButton(Modifier.fillMaxSize().padding(5.dp), onPressed = onNext)
        }
        Row(Modifier.padding(5.dp).fillMaxHeight().fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly) {
            inputs.DefenseQuality(Modifier.fillMaxHeight().fillMaxWidth(0.33F), title = "Defense:\n1 = Worst - 3 = Best")

            inputs.Notes(Modifier.fillMaxHeight().fillMaxWidth(0.75F))
            PageChanger(Modifier.fillMaxSize().padding(5.dp), onNext = {}, onBack = onBack, nextEnabled = false)
        }
    }
}