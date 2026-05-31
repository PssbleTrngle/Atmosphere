plugins {
    id("com.possible-triangle.neoforge")
}

upload {
    maven.nexus()
    modrinth.syncBodyFromReadme()
}

enableSpotless()
