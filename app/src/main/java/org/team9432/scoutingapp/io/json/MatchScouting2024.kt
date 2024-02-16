package org.team9432.scoutingapp.io.json

import kotlinx.serialization.Serializable
import org.team9432.scoutingapp.annotation.*
import org.team9432.scoutingapp.io.config

@Serializable
@InputBase
data class MatchScoutingData(
    @InlineTextInputField(numberOnly = true) val scoutName: String = "",

    // This could potentially be done automatically based on team number + match number
    @CycleInputField("Red", "Blue") val alliance: String = "N/A",

    // Consider adding a field image input module for this
    @CycleInputField("Left", "Center", "Right") val autoStartingPosition: String = "N/A",

    @NumberInputField val autoScoredSpeaker: Int = 0,
    @NumberInputField val autoScoredAmp: Int = 0,
    @SwitchInputField val crossedAutoLine: Boolean = false,

    @SwitchInputField val pickupAutoNoteOne: Boolean = false,
    @SwitchInputField val pickupAutoNoteTwo: Boolean = false,
    @SwitchInputField val pickupAutoNoteThree: Boolean = false,
    @SwitchInputField val pickupAutoNoteFour: Boolean = false,
    @SwitchInputField val pickupAutoNoteFive: Boolean = false,

    @NumberInputField val teleSpeakerNotes: Int = 0,
    @NumberInputField val teleSpeakerNotesMissed: Int = 0,
    @NumberInputField val teleAmpNotes: Int = 0,
    @NumberInputField val teleAmpNotesMissed: Int = 0,

    @NumberInputField val scoredTrap: Int = 0,
    @CycleInputField("No Attempt", "Parked", "Single", "Harmony x1", "Harmony x2") val endgame: String = "N/A",
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
                        autoScoredSpeaker + del +
                        autoScoredAmp + del +

                        pickupAutoNoteOne + del +
                        pickupAutoNoteTwo + del +
                        pickupAutoNoteThree + del +
                        pickupAutoNoteFour + del +
                        pickupAutoNoteFive + del +

                        autoStartingPosition + del +
                        crossedAutoLine + del +

                        teleAmpNotes + del +
                        teleAmpNotesMissed + del +
                        teleSpeakerNotes + del +
                        teleSpeakerNotesMissed + del +

                        endgame + del +
                        scoredTrap + del +
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
