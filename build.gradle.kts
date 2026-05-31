plugins {
    id("com.possible-triangle.neoforge")
}

// TODO move to GradleHelper
val patch = env["PATCH_VERSION"] ?: "999"
mod.version =
    providers
        .gradleProperty("mod_version")
        .map { it.replace("<patch>", patch) }

neoforge {
    dataGen {
        splitSourceSet()
    }
}

upload {
    maven.nexus()
    modrinth.syncBodyFromReadme()
}

enableSpotless()
