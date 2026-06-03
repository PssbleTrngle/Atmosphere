plugins {
    id("com.possible-triangle.neoforge")
}

neoforge {
    dependOn(project(":api"))
    dependOn(project(":common"))

    accessTransformer(project(":common"))

    dataGen {
        splitSourceSet()
    }
}

neoForge.runs {
    removeIf { it.type.get() != "data" }
}

dependencies {
    modInclude(libs.ponder)

    modCompileOnly(libs.sable)
}
