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

        maven {
            url = uri("https://maven.blamejared.com/")
            content {
                includeGroup("foundry.veil")
                includeGroup("gg.moonflower")
                includeGroup("io.github.ocelot")
            }
        }

        maven {
            url = uri("https://mvn.devos.one/snapshots")
            content {
                includeGroup("com.tterrag.registrate")
            }
        }
    }

    upload.maven.nexus()
}

enableSpotless()
