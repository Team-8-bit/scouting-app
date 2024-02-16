package org.team9432.scoutingapp.io

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.team9432.scoutingapp.io.json.DataType
import org.team9432.scoutingapp.io.json.MatchData
import org.team9432.scoutingapp.io.json.MatchScoutingData
import org.team9432.scoutingapp.io.json.SuperscoutingData
import org.team9432.scoutingapp.io.kindle.StorageInterface

object MatchStorageInterface {
    private val json = Json {
//        ignoreUnknownKeys = true
        prettyPrint = true
        encodeDefaults = true
    }

    fun getMatchDataOrNew(team: String, matchNumber: String): MatchScoutingData {
        val file = getFileName(team, matchNumber, DataType.MATCH_SCOUT)

        val eventID = config.eventID
        return if (StorageInterface.checkExistence("$eventID/$file")) {
            (json.decodeFromString(StorageInterface.readText("$eventID/$file")) as MatchData).data as MatchScoutingData
        } else {
            MatchScoutingData()
        }
    }

    fun getSuperDataOrNew(team: String, matchNumber: String): SuperscoutingData {
        val file = getFileName(team, matchNumber, DataType.SUPERSCOUT)

        val eventID = config.eventID
        return if (StorageInterface.checkExistence("$eventID/$file")) {
            (json.decodeFromString(StorageInterface.readText("$eventID/$file")) as MatchData).data as SuperscoutingData
        } else {
            SuperscoutingData()
        }
    }

    fun deleteMatch(team: String, matchNumber: String, dataType: DataType) {
        StorageInterface.deleteFile("${config.eventID}/${getFileName(team, matchNumber, dataType)}")
    }

    fun matchDataExists(team: String, matchNumber: String, dataType: DataType) = StorageInterface.checkExistence("${config.eventID}/${getFileName(team, matchNumber, dataType)}")
    fun getMatchData(team: String, matchNumber: String, dataType: DataType): MatchData = json.decodeFromString(StorageInterface.readText("${config.eventID}/${getFileName(team, matchNumber, dataType)}"))

    fun writeData(data: MatchData) {
        val fileName = getFileName(data.metadata.teamNumber, data.metadata.matchNumber, data.metadata.dataType)

        val fileInput = json.encodeToString(data)

        StorageInterface.writeText("${config.eventID}/$fileName", fileInput)
    }

    private fun getFileName(teamNumber: String, matchNumber: String, dataType: DataType): String {
        return "$teamNumber-$matchNumber-$dataType.json"
    }
}