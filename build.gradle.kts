plugins {
    java
    id("org.springframework.boot") version "3.4.4"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "org.esjo"
version = "0.0.1-SNAPSHOT"

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

springBoot {
    mainClass.set("org.esjo.outfitcoordination.OutfitCoordinationApplication")
}

repositories {
    mavenCentral()
}


dependencies {
    // spring
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // springdoc
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.6")

    // database
    implementation("org.flywaydb:flyway-core")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("com.h2database:h2")

    // querydsl
    implementation("com.querydsl:querydsl-jpa:5.1.0:jakarta")
    annotationProcessor("com.querydsl:querydsl-apt:5.1.0:jakarta")

    compileOnly("jakarta.persistence:jakarta.persistence-api")
    annotationProcessor("jakarta.persistence:jakarta.persistence-api")

    // lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    // mapstruct
    implementation("org.mapstruct:mapstruct:1.5.5.Final")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")
    annotationProcessor("org.projectlombok:lombok-mapstruct-binding:0.2.0")

    // test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}


// querydsl <
val generatedSourcesDir = layout.buildDirectory.dir("generated/sources/querydsl").get().asFile

sourceSets {
    main {
        java {
            srcDirs += generatedSourcesDir
        }
    }
}

tasks.register<JavaCompile>("generateQuerydslSources") {
    group = "build"
    description = "Generates QueryDSL Q classes"
    classpath = sourceSets.main.get().compileClasspath
    source = sourceSets.main.get().java
    destinationDirectory.set(generatedSourcesDir)
    options.annotationProcessorPath = configurations.annotationProcessor.get()
}

tasks.named<JavaCompile>("compileJava") {
    dependsOn("generateQuerydslSources")
}
// querydsl >

tasks.withType<JavaCompile>().configureEach {
    options.compilerArgs.addAll(
        arrayOf(
            // mapstruct
            "-Amapstruct.suppressGeneratorTimestamp=true",
            "-Amapstruct.unmappedTargetPolicy=ERROR",
            "-Amapstruct.defaultComponentModel=spring",
        ),
    )
}

// test
tasks.withType<Test> {
    useJUnitPlatform()
}
