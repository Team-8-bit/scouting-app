package org.team9432.scoutingapp.io.json

import kotlinx.serialization.Serializable

@Serializable
data class Schedule(
    val eventID: String,
    val superscoutIDs: List<Int>, // A list of scout IDs
    val scoutingSchedule: Map<Int, ScoutingSchedule> // A map of scout IDs to their schedules
)

@Serializable
data class ScoutingSchedule(
    val matches: Map<String, String> // A map of match numbers to team numbers
)