import java.util.*

plugins {
    java
    id("org.springframework.boot") version "3.4.3"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.omnichat"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

extra["springAiVersion"] = "1.0.0-M6"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.ai:spring-ai-openai-spring-boot-starter")
    implementation("org.springframework.ai:spring-ai-anthropic-spring-boot-starter")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.register<Exec>("buildReact") {
    workingDir = file("./frontend")
    inputs.dir("./frontend")
    group = "build"
    val osName = System.getProperty("os.name").lowercase(Locale.ROOT)
    commandLine = if (osName.contains("windows")) {
        listOf("npm.cmd", "run", "build")
    } else {
        listOf("npm", "run", "build")
    }
}

tasks.register<Copy>("copyReactBuild") {
    dependsOn("buildReact")
    from("./frontend/dist")
    into("./build/resources/main/static")
}

tasks.named("processResources") {
    dependsOn("copyReactBuild")
}
dependencyManagement {
    imports {
        mavenBom("org.springframework.ai:spring-ai-bom:${project.extra["springAiVersion"]}")
    }
}

