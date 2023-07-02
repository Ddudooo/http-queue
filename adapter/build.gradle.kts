dependencies {
    api(project(":adapter:slack"))
    api(project(":adapter:web"))
}

subprojects {
    apply {
        plugin("org.jetbrains.kotlin.plugin.spring")
    }

    dependencies {
        implementation(project(":core"))
    }
}
