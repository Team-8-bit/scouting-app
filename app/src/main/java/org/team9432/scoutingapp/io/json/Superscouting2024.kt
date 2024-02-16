package org.team9432.scoutingapp.io.json

import kotlinx.serialization.Serializable
import org.team9432.scoutingapp.annotation.*

@Serializable
@InputBase
data class SuperscoutingData(
    @InlineTextInputField(numberOnly = true) val scoutName: String = "",

    @CycleInputField("Red", "Blue") val alliance: String = "N/A",


    @CycleInputField("Amp", "Speaker", "Both") val primaryScoringLocation: String = "N/A",
    @CycleInputField("Source", "Ground", "Both") val primaryPickupLocation: String = "N/A",

    @NumberInputField val amplifierActivated: Int = 0,
    @SwitchInputField val coopActivated: Boolean = false,

    @CycleInputField("Primary Scorer", "Amp Scorer", "Speaker Scorer", "Defense") val robotRole: String = "N/A",
    @SwitchInputField val wasDefended: Boolean = true,

    @NumberInputField val cycles: Int = 0,

    @NumberInputField(max = 3, min = 1) val defenseQuality: Int = 2,
    @NumberInputField(max = 3, min = 1) val drivingQuality: Int = 2,

    @NumberInputField val penalties: Int = 0,
    @SwitchInputField val disabled: Boolean = false,
    @TextInputField val notes: String = "",
): ScoutingData {
    private val del = ";"
    val qrString
        get() = (
                        alliance + del +
                        scoutName + del +

                        primaryScoringLocation + del +
                        primaryPickupLocation + del +

                        amplifierActivated + del +
                        coopActivated + del +

                        robotRole + del +
                        wasDefended + del +

                        cycles + del +
                        penalties + del +
                        defenseQuality + del +
                        drivingQuality + del +
                        disabled + del
                ).replace("true", "T").replace("false", "F") +
                notes.trim()

    override fun getSerializedQROutput(): String {
        TODO("Not yet implemented")
    }
}
