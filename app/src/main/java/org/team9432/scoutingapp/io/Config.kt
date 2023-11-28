package org.team9432.scoutingapp.io

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.team9432.scoutingapp.io.SDCard.CONFIG_FILE

var config by mutableStateOf(readConfig())
    private set

fun updateConfig(newConfig: Config) {
    config = newConfig
    if (!CONFIG_FILE.exists()) CONFIG_FILE.createNewFile()
    val jsonString = Json.encodeToString(newConfig)
    CONFIG_FILE.writeText(jsonString)
}

private fun readConfig(): Config {
    if (!CONFIG_FILE.exists()) CONFIG_FILE.createNewFile()
    val jsonString = CONFIG_FILE.readText()
    return if (jsonString.isNotBlank()) {
        Json.decodeFromString<Config>(jsonString)
    } else {
        Config()
    }
}

@Serializable
data class Config(val darkMode: Boolean = true)