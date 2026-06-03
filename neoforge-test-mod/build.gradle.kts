import org.gradle.internal.extensions.stdlib.capitalized

plugins {
    id("com.possible-triangle.neoforge")
}

neoforge {
    accessTransformer(project(":common"))

    dataGen {
        owner = project
        splitSourceSet()
    }
}

neoForge.runs.forEach { run ->
    run.ideName = "Test Mod ${run.name.capitalized()}"
}

dependencies {
    api(project(":neoforge"))

    modImplementation(libs.sable)
    modImplementation(libs.create.simulated) { isTransitive = false }
    modApi(libs.registrate)
}

tasks.publish {
    onlyIf { false }
}
