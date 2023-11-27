package org.team9432.scoutingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import org.team9432.scoutingapp.io.resolver
import org.team9432.scoutingapp.ui.screens.InitialScreen
import org.team9432.scoutingapp.ui.theme.AppTheme


class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        resolver = contentResolver
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme(darkTheme = true) {
                when (appState.screen) {
                    Screen.INITIAL_SCREEN -> InitialScreen()
                }
            }
        }
    }
}