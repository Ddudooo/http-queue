import org.springframework.boot.gradle.tasks.bundling.BootBuildImage
import org.springframework.boot.gradle.tasks.bundling.BootJar
import org.springframework.boot.gradle.tasks.run.BootRun

apply {
    plugin("org.jetbrains.kotlin.plugin.spring")
}

dependencies {
    implementation(project(":adapter"))
    implementation(project(":core"))

    developmentOnly("org.springframework.boot:spring-boot-devtools")
    developmentOnly("org.springframework.boot:spring-boot-docker-compose")
}

tasks.withType<BootJar> {
    enabled = true
}

tasks.withType<BootRun> {
    enabled = true
}

tasks.withType<BootBuildImage> {
    enabled = true
}
