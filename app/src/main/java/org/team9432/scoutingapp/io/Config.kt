package org.team9432.scoutingapp.io

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import org.team9432.scoutingapp.io.json.json
import org.team9432.scoutingapp.io.kindle.StorageInterface

var config by mutableStateOf(readData())
    private set

private const val fileName = "config.json"

fun updateConfig(newConfig: Config) {
    config = newConfig
    val jsonString = json.encodeToString(newConfig)
    StorageInterface.writeText(fileName, jsonString)
}

private fun readData(): Config {
    val jsonString = StorageInterface.readText(fileName)
    return if (jsonString.isNotBlank()) {
        json.decodeFromString(jsonString)
    } else {
        Config()
    }
}

@Serializable
data class Config(
    val darkMode: Boolean = true,
    val eventID: String = "2023azgl",
    val scoutID: Int = 1,
    val isSuperscout: Boolean = false,
    val debugMode: Boolean = false,
)