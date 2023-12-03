package org.team9432.scoutingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.team9432.scoutingapp.io.ScheduleFiles
import org.team9432.scoutingapp.io.config
import org.team9432.scoutingapp.ui.NavigationRail
import org.team9432.scoutingapp.ui.screens.DebugScreen
import org.team9432.scoutingapp.ui.screens.MatchScoutingScreen
import org.team9432.scoutingapp.ui.screens.MatchSelectionScreen
import org.team9432.scoutingapp.ui.screens.SettingsScreen
import org.team9432.scoutingapp.ui.theme.AppTheme


class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme(darkTheme = config.darkMode) {
                Surface(color = MaterialTheme.colorScheme.background) {
                    Row(Modifier.fillMaxWidth()) {
                        Surface(modifier = Modifier.padding(top = 10.dp, start = 10.dp, end = 10.dp, bottom = 10.dp), shape = MaterialTheme.shapes.large) {
                            NavigationRail()
                        }
                        Surface(modifier = Modifier.padding(top = 10.dp, end = 10.dp, bottom = 10.dp), shape = MaterialTheme.shapes.large) {
                            when (appScreen) {
                                Screen.DEBUG -> DebugScreen()
                                Screen.SETTINGS -> SettingsScreen()
                                Screen.MATCH_SELECTION -> MatchSelectionScreen(ScheduleFiles.getMatches())
                                Screen.MATCH_SCOUTING_SCREEN -> {
                                    val matchNumber = currentMatch
                                    MatchScoutingScreen(ScheduleFiles.getTeamToScout(matchNumber), matchNumber)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}