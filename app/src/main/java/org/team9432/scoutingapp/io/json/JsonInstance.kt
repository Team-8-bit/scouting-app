package org.team9432.scoutingapp.io.json

import kotlinx.serialization.json.Json

val json = Json {
//        ignoreUnknownKeys = true
    prettyPrint = true
    encodeDefaults = true
}