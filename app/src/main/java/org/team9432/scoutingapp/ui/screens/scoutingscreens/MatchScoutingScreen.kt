package org.team9432.scoutingapp.ui.screens.scoutingscreens

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import org.team9432.scoutingapp.io.config
import org.team9432.scoutingapp.ui.screens.scoutingscreens.matchscouting.MatchScouting


@Composable
fun MatchScoutingScreen(teamToScout: String, matchNumber: Int) {
    if (config.isSuperscout) {
        MatchScouting(teamToScout, matchNumber)
    } else {
        MatchScouting(teamToScout, matchNumber)
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