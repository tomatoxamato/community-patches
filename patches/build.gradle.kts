group = "app.patchforge"

patches {
    about {
        name = "Tomatoxamato Community Patches"
        description = "Community patches maintained by tomatoxamato"
        source = "https://github.com/tomatoxamato/community-patches"
        author = "tomatoxamato"
        contact = "https://github.com/tomatoxamato/community-patches/issues"
        website = "https://github.com/tomatoxamato/community-patches"
        license = "GPLv3"
    }
}

// Separate configuration so gson is available at runtime for the
// generatePatchesList task but never bundled into the APK.
val patchListGeneratorClasspath = configurations.create("patchListGeneratorClasspath")

dependencies {
    compileOnly(libs.gson)
    patchListGeneratorClasspath(libs.gson)
}

tasks {
    register<JavaExec>("generatePatchesList") {
        description = "Build patch with patch list"

        dependsOn(build)

        classpath = sourceSets["main"].runtimeClasspath + patchListGeneratorClasspath
        mainClass.set("util.PatchListGeneratorKt")
    }

    // Used by gradle-semantic-release-plugin.
    publish {
        dependsOn("generatePatchesList")
    }
}
