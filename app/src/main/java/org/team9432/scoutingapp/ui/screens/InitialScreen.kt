package org.team9432.scoutingapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.team9432.scoutingapp.DEFAULT_EVENT_CODE
import org.team9432.scoutingapp.io.SDCard
import org.team9432.scoutingapp.matchScoutingData
import org.team9432.scoutingapp.pitScoutingData

private val idRegex = """\d\d\d\d\w+""".toRegex()

fun getFilePredicate(id: String, type: String) = { fileName: String -> fileName.contains(id, ignoreCase = true) && fileName.contains(type, ignoreCase = true) }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InitialScreen() {
    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            modifier = Modifier.padding(10.dp),
            text = "Enter event ID",
            style = MaterialTheme.typography.headlineLarge
        )

        var matchFile by remember { mutableStateOf(SDCard.getFile(getFilePredicate(DEFAULT_EVENT_CODE, "Match"))) }
        var pitFile by remember { mutableStateOf(SDCard.getFile(getFilePredicate(DEFAULT_EVENT_CODE, "Pit"))) }

        var currentSearch by remember { mutableStateOf(DEFAULT_EVENT_CODE) }
        OutlinedTextField(
            modifier = Modifier.padding(10.dp),
            value = currentSearch,
            placeholder = { Text("Event ID") },
            onValueChange = { search ->
                currentSearch = search
                if (search.matches(idRegex)) {
                    matchFile = SDCard.getFile(getFilePredicate(currentSearch, "Match"))
                    pitFile = SDCard.getFile(getFilePredicate(currentSearch, "Pit"))
                } else {
                    matchFile = null
                    pitFile = null
                }
            }
        )

        Row(Modifier.padding(10.dp)) {
            OutlinedButton(
                onClick = {
                    currentSearch = ""
                    matchFile = null
                    pitFile = null
                }
            ) {
                Text(text = "Clear")
            }
            Spacer(Modifier.fillMaxWidth(0.1F))
            FilledTonalButton(
                onClick = {
                    matchScoutingData = matchFile?.let { SDCard.getMatchSchedule(it) }
                    pitScoutingData = pitFile?.let { SDCard.getPitSchedule(it) }
                }
            ) {
                Text(text = "Select")
            }
        }

        Column(
            Modifier
                .padding(10.dp)
                .align(Alignment.Start)
        ) {
            Text(text = "Match Scouting File", style = MaterialTheme.typography.labelLarge)
            Text(text = matchFile?.name ?: "Missing")
        }
        Column(
            Modifier
                .padding(10.dp)
                .align(Alignment.Start)
        ) {
            Text(text = "Pit Scouting File", style = MaterialTheme.typography.labelLarge)
            Text(text = pitFile?.name ?: "Missing")
        }
    }
}


