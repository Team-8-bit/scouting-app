package org.team9432.scoutingapp.io.json

import kotlinx.serialization.Serializable
import org.team9432.scoutingapp.annotation.*

@Serializable
@InputBase
data class SuperscoutingData(
    @InlineTextInputField val scoutName: String = "",

    @CycleInputField("Red", "Blue") val alliance: String = "N/A",

    @CycleInputField("Amp", "Speaker", "Both") val primaryScoringLocation: String = "N/A",
    @CycleInputField("Source", "Ground", "Both") val primaryPickupLocation: String = "N/A",

    @NumberInputField val amplifierActivated: Int = 0,
    @SwitchInputField val coopActivated: Boolean = false,

    @CycleInputField("Primary Scorer", "Amp Scorer", "Speaker Scorer", "Defense") val robotRole: String = "N/A",
    @SwitchInputField val wasDefended: Boolean = false,

    @NumberInputField val cycles: Int = 0,

    @NumberInputField(max = 3, min = 0) val defenseQuality: Int = 2,
    @NumberInputField(max = 3, min = 1) val drivingQuality: Int = 2,

    @NumberInputField val penalties: Int = 0,
    @TextInputField val comments: String = "",
): ScoutingData {
    override fun getSerializedQROutput(): String {
        return listOf(
            scoutName,
            alliance,
            primaryScoringLocation,
            primaryPickupLocation,
            amplifierActivated,
            coopActivated,
            robotRole,
            wasDefended,
            cycles,
            penalties,
            defenseQuality,
            drivingQuality,
        ).process() + comments.trim()
    }
}
