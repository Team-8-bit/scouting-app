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

object MatchScoutFile {
    private val json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
    }

    private val EVENT_DATA_DIR = File(SDCard.MAIN_FOLDER, config.eventID)
    private val MATCH_SCOUTING_DATA_FILE = File(EVENT_DATA_DIR, "MatchScoutingData.json")

    var data by mutableStateOf(readData())
        private set

    fun addMatch(match: MatchScoutingMatchData) {
        updateData(data.copy(matches = data.matches.toMutableMap().also { it[match.matchNumber] = match }))
    }

    fun ScheduledMatch.hasBeenScouted(): Boolean {
        return data.matches[this.number]?.data != null
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
        if (!MATCH_SCOUTING_DATA_FILE.exists()) MATCH_SCOUTING_DATA_FILE.createNewFile()
        if (!EVENT_DATA_DIR.exists()) EVENT_DATA_DIR.mkdirs()
    }
}

