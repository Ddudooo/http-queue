dependencies {
    api(project(":persistence:database"))
}

subprojects {
    dependencies {
        implementation(project(":core:common:enum"))
    }
}
