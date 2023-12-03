val kspVersion: String by project

plugins {
    kotlin("jvm")
}

group = "org.team9432.lib.annotation"

dependencies {
    implementation("com.squareup:kotlinpoet:1.14.2")
    implementation("com.squareup:kotlinpoet-ksp:1.14.2")
    implementation("com.google.devtools.ksp:symbol-processing-api:1.9.21-1.0.15")
}

sourceSets.main {
    java.srcDirs("src/main/kotlin")
}

