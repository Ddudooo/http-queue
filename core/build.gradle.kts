dependencies {
    api(project(":core:common"))
    api(project(":core:domain"))
}

subprojects {
    apply {
        plugin("org.jetbrains.kotlin.plugin.spring")
    }

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter")
        implementation("org.springframework.boot:spring-boot-starter-web")
    }
}
