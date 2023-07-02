plugins {
    kotlin("plugin.spring")
    kotlin("plugin.jpa")
}

dependencies {
    runtimeOnly("com.h2database:h2")
    runtimeOnly("org.postgresql:postgresql")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // ULID CREATOR
    implementation("com.github.f4b6a3:ulid-creator:5.2.0")
}
