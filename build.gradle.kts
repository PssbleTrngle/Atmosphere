plugins {
    id("com.possible-triangle.core")
    id("com.possible-triangle.common") apply false
    id("com.possible-triangle.neoforge") apply false
}

// TODO move to GradleHelper
val patch = env["PATCH_VERSION"] ?: "999"
mod.version =
    providers
        .gradleProperty("mod_version")
        .map { it.replace("<patch>", patch) }

subprojects {
    apply(plugin = "com.possible-triangle.core")

    upload {
        maven.nexus()
    }
}

enableSpotless()
