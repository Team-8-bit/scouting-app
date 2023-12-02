package org.team9432.scoutingapp.io

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.team9432.scoutingapp.io.data.MatchScoutingData
import org.team9432.scoutingapp.io.data.MatchScoutingFile
import java.io.File

object MatchScoutingFile {
    private val json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
        encodeDefaults = true
    }

    private val EVENT_DATA_DIR = File(SDCard.MAIN_FOLDER, config.eventID)
    private val MATCH_SCOUTING_DATA_FILE = File(EVENT_DATA_DIR, "MatchScoutingData.json")

    var dataFile by mutableStateOf(readData())
        private set

    fun deleteMatchData(teamScouted: String, matchNumber: Int) {
        val newData = dataFile.matches.toMutableMap()
        val newMatchData = newData[matchNumber]?.toMutableMap() ?: mutableMapOf()
        newMatchData.remove(teamScouted)
        newData[matchNumber] = newMatchData
        updateDataFile(dataFile.copy(matches = newData))
    }

    fun addMatchData(matchData: MatchScoutingData) {
        val matchNumber = matchData.matchNumber.toInt()
        val newData = dataFile.matches.toMutableMap()
        val newMatchData = newData[matchNumber]?.toMutableMap() ?: mutableMapOf()
        newMatchData[matchData.teamNumber] = matchData
        newData[matchNumber] = newMatchData
        updateDataFile(dataFile.copy(matches = newData))
    }

    fun hasBeenScouted(matchNumber: Int, team: String): Boolean {
        return dataFile.matches[matchNumber]?.get(team) != null
    }

    private fun updateDataFile(data: MatchScoutingFile) {
        createFiles()
        this.dataFile = data
        val jsonString = json.encodeToString(data)
        MATCH_SCOUTING_DATA_FILE.writeText(jsonString)
    }

    private fun readData(): MatchScoutingFile {
        createFiles()
        val jsonString = MATCH_SCOUTING_DATA_FILE.readText()
        return if (jsonString.isNotBlank()) {
            json.decodeFromString<MatchScoutingFile>(jsonString)
        } else {
            MatchScoutingFile(emptyMap())
        }
    }

    private fun createFiles() {
        if (!EVENT_DATA_DIR.exists()) EVENT_DATA_DIR.mkdirs()
        if (!MATCH_SCOUTING_DATA_FILE.exists()) MATCH_SCOUTING_DATA_FILE.createNewFile()
    }
}

