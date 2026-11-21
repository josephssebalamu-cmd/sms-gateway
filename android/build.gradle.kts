import com.android.build.gradle.LibraryExtension

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

val newBuildDir: Directory = rootProject.layout.buildDirectory.dir("../../build").get()
rootProject.layout.buildDirectory.value(newBuildDir)

subprojects {
    val newSubprojectBuildDir: Directory = newBuildDir.dir(project.name)
    project.layout.buildDirectory.value(newSubprojectBuildDir)
}

subprojects {
    project.evaluationDependsOn(":app")
}

// 👇 ADD THIS BLOCK TO FIX THE MISSING NAMESPACE 👇
subprojects {
    afterEvaluate {
        extensions.findByType<LibraryExtension>()?.apply {
            if (namespace == null) {
                namespace = "com.fasbec.plugins.${project.name}"
            }
        }
    }
}
// 👆 END OF FIX 👆

tasks.register<Delete>("clean") {
    delete(rootProject.layout.buildDirectory)
}