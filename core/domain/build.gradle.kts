dependencies {
    api(project(":core:domain:queue"))
}

subprojects {
    dependencies {
        implementation(project(":core:common"))

        api("org.springframework.boot:spring-boot-starter-data-jpa")
    }
}
