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

// ✅ Step 1: Inject namespaces into plugins that lack them
// This MUST run before evaluationDependsOn(":app") below
subprojects {
    afterEvaluate {
        extensions.findByType<LibraryExtension>()?.apply {
            if (namespace == null) {
                namespace = "com.fasbec.plugins.${project.name}"
            }
        }
    }
}

// ✅ Step 2: Evaluate the app project (now safe because namespaces are set)
subprojects {
    project.evaluationDependsOn(":app")
}

tasks.register<Delete>("clean") {
    delete(rootProject.layout.buildDirectory)
}