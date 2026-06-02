plugins {
    id("com.possible-triangle.core")
    id("com.possible-triangle.common") apply false
    id("com.possible-triangle.neoforge") apply false
}

subprojects {
    apply(plugin = "com.possible-triangle.core")

    repositories {
        maven {
            url = uri("https://maven.createmod.net")
            content {
                includeGroup("com.simibubi.create")
                includeGroup("net.createmod.ponder")
                includeGroup("dev.engine-room.flywheel")
            }
        }

        maven {
            url = uri("https://maven.ryanhcode.dev/releases")
            content {
                includeGroupAndSubgroups("dev.eriksonn")
                includeGroupAndSubgroups("dev.ryanhcode")
                includeGroupAndSubgroups("dev.simulated_team")
            }
        }
    }

    upload {
        maven.nexus()
    }
}

enableSpotless()
