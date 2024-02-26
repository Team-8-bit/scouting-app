package org.team9432.scoutingapp.io.json

import kotlinx.serialization.Serializable

const val OUTPUT_DELIMITER = ";"

@Serializable
data class MatchData(
    val metadata: MatchMetadata,
    val data: ScoutingData,
): QRSerializable {
    override fun getSerializedQROutput(): String {
        return metadata.getSerializedQROutput() + data.getSerializedQROutput()
    }
}

@Serializable
data class MatchMetadata(
    val dataType: DataType,
    val matchNumber: String,
    val teamNumber: String,
    val scoutID: String,
): QRSerializable {
    override fun getSerializedQROutput() = listOf(matchNumber, teamNumber).process()
}

@Serializable
sealed interface ScoutingData: QRSerializable

interface QRSerializable {
    fun getSerializedQROutput(): String
}

fun List<Any>.process(): String {
    return joinToString(OUTPUT_DELIMITER) {
        when (it) {
            true -> "T"
            false -> "F"
            DataType.SUPERSCOUT -> "SS"
            DataType.MATCH_SCOUT -> "MS"
            "N/A" -> "*"
            "Red" -> "R"
            "Blue" -> "B"
            else -> it.toString()
        }
    } + OUTPUT_DELIMITER
}

enum class DataType {
    SUPERSCOUT, MATCH_SCOUT
}