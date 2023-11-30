package org.team9432.scoutingapp.io

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.team9432.scoutingapp.io.data.MatchScoutingData
import org.team9432.scoutingapp.io.data.MatchScoutingMatchData
import org.team9432.scoutingapp.io.data.ScheduledMatch
import java.io.File

object MatchScoutingFile {
    private val json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
        encodeDefaults = true
    }

    private val EVENT_DATA_DIR = File(SDCard.MAIN_FOLDER, config.eventID)
    private val MATCH_SCOUTING_DATA_FILE = File(EVENT_DATA_DIR, "MatchScoutingData.json")

    var data by mutableStateOf(readData())
        private set

    fun deleteMatch(teamScouted: String, matchNumber: Int) {
        val newMatches = data.matches.toMutableMap()
        val newMatchData = newMatches[matchNumber]?.toMutableMap() ?: mutableMapOf()
        newMatchData.remove(teamScouted)
        newMatches[matchNumber] = newMatchData
        updateData(data.copy(matches = newMatches))
    }

    fun addTeamToMatch(teamScouted: String, match: MatchScoutingMatchData) {
        val newMatches = data.matches.toMutableMap()
        val newMatchData = newMatches[match.matchNumber]?.toMutableMap() ?: mutableMapOf()
        newMatchData[teamScouted] = match
        newMatches[match.matchNumber] = newMatchData
        updateData(data.copy(matches = newMatches))
    }

    fun ScheduledMatch.hasBeenScouted(team: String): Boolean {
        return data.matches[this.number]?.get(team) != null
    }

    fun updateData(data: MatchScoutingData) {
        createFiles()
        this.data = data
        val jsonString = json.encodeToString(data)
        MATCH_SCOUTING_DATA_FILE.writeText(jsonString)
    }

    private fun readData(): MatchScoutingData {
        createFiles()
        val jsonString = MATCH_SCOUTING_DATA_FILE.readText()
        return if (jsonString.isNotBlank()) {
            json.decodeFromString<MatchScoutingData>(jsonString)
        } else {
            MatchScoutingData(emptyMap())
        }
    }

    private fun createFiles() {
        if (!EVENT_DATA_DIR.exists()) EVENT_DATA_DIR.mkdirs()
        if (!MATCH_SCOUTING_DATA_FILE.exists()) MATCH_SCOUTING_DATA_FILE.createNewFile()
    }
}

