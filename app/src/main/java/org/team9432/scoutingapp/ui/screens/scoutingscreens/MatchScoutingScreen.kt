package org.team9432.scoutingapp.ui.screens.scoutingscreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.team9432.scoutingapp.io.config
import org.team9432.scoutingapp.ui.screens.scoutingscreens.matchscouting.MatchScouting
import org.team9432.scoutingapp.ui.screens.scoutingscreens.superscouting.Superscouting


@Composable
fun MatchScoutingScreen(teamToScout: String, matchNumber: String) {
    Column {
        Box(Modifier.fillMaxWidth().height(20.dp).background(MaterialTheme.colorScheme.surface), contentAlignment = Alignment.Center) {
            Text("Team to Scout: $teamToScout", textAlign = TextAlign.Center)
        }
        if (config.isSuperscout) {
            Superscouting(teamToScout, matchNumber)
        } else {
            MatchScouting(teamToScout, matchNumber)
        }
    }
}

@Composable
fun Dialog(
    title: String,
    body: String,
    onDismiss: () -> Unit,
    onAgree: () -> Unit,
) {
    AlertDialog(
        title = { Text(text = title) },
        text = { Text(text = body) },
        onDismissRequest = onDismiss,
        confirmButton = { TextButton(onClick = onAgree) { Text("Confirm") } },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel") } }
    )
}