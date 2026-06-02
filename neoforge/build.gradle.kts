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
