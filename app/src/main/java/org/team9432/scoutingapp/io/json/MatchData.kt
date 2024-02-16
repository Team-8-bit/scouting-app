package org.team9432.scoutingapp.io.json

import kotlinx.serialization.Serializable

@Serializable
data class MatchData(
    val metadata: MatchMetadata,
    val data: ScoutingData,
): QRSerializable {
    override fun getSerializedQROutput(): String {
        return "temp"
    }
}

@Serializable
data class MatchMetadata(
    val dataType: DataType,
    val matchNumber: String,
    val teamNumber: String,
    val scoutID: String,
): QRSerializable {
    override fun getSerializedQROutput(): String {
        TODO("Not yet implemented")
    }
}

@Serializable
sealed interface ScoutingData: QRSerializable

interface QRSerializable {
    fun getSerializedQROutput(): String
}

enum class DataType {
    SUPERSCOUT, MATCH_SCOUT
}