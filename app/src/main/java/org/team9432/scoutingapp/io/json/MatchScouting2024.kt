package org.team9432.scoutingapp.io.json

import kotlinx.serialization.Serializable
import org.team9432.scoutingapp.annotation.*

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
    @TextInputField val notes: String = "",
): ScoutingData {
    override fun getSerializedQROutput(): String {
        return listOf(
            scoutName,
            alliance,
            autoStartingPosition,
            autoScoredSpeaker,
            autoScoredAmp,
            crossedAutoLine,
            pickupAutoNoteOne,
            pickupAutoNoteTwo,
            pickupAutoNoteThree,
            pickupAutoNoteFour,
            pickupAutoNoteFive,
            teleSpeakerNotes,
            teleSpeakerNotesMissed,
            teleAmpNotes,
            teleAmpNotes,
            scoredTrap,
            endgame,
        ).process() + notes.trim()
    }
}
