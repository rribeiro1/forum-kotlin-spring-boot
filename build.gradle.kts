import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.0.5"
	id("io.spring.dependency-management") version "1.1.0"
	kotlin("jvm") version "1.7.22"
	kotlin("plugin.spring") version "1.7.22"
	kotlin("plugin.jpa") version "1.7.22"
	kotlin("kapt") version "1.7.22"
	id("com.diffplug.spotless") version "6.12.0"
	id ("org.flywaydb.flyway") version "7.7.0"
	id("jacoco")
	id("idea")
	id("java")
}

group = "io.rafaelribeiro"
java.sourceCompatibility = JavaVersion.VERSION_17
java.targetCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

dependencies {
	// -- Kotlin
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	// -- Spring
	kapt("org.springframework.boot:spring-boot-configuration-processor")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-cache")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-graphql")
	developmentOnly("org.springframework.boot:spring-boot-devtools")

	// -- Others
	implementation("jakarta.xml.bind:jakarta.xml.bind-api:4.0.0")
	implementation("org.springdoc:springdoc-openapi-ui:1.6.15")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("javax.validation:validation-api:2.0.1.Final")
	implementation("me.paulschwarz:spring-dotenv:3.0.0")

	// -- JWT
	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

	// -- Tracing
	implementation("com.datadoghq:dd-trace-api:1.10.0")
	implementation("io.opentracing:opentracing-api:0.33.0")
	implementation("io.opentracing:opentracing-util:0.33.0")

	// -- Database
	runtimeOnly("org.flywaydb:flyway-core")
	runtimeOnly("org.postgresql:postgresql")
	runtimeOnly("com.h2database:h2")
	implementation("org.hibernate:hibernate-jpamodelgen:6.2.3.Final")
	implementation("org.hibernate:hibernate-validator:8.0.0.Final")

	// -- Testing
	testImplementation("io.rest-assured:rest-assured")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.graphql:spring-graphql-test")
	testImplementation("io.mockk:mockk:1.13.4")
	testImplementation("com.ninja-squad:springmockk:4.0.2")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

jacoco {
	toolVersion = "0.8.7"
}

tasks.jacocoTestReport {
	group = "Reporting"
	reports {
		xml.required.set(true)
		csv.required.set(false)
		html.required.set(false)
	}
}

tasks.register("jacocoFixForCodeClimate", Copy::class.java) {
	from("build/reports/jacoco/test/jacocoTestReport.xml")
	into("build/reports/jacoco/test/")
	filter { line ->
		line.replace("io/rafaelribeiro/", "src/main/kotlin/io/rafaelribeiro/")
	}
	rename { fileName ->
		fileName.replace("jacocoTestReport.xml", "jacoco.xml")
	}
}

spotless {
	format("misc") {
		target(".gitignore", "*.md", "*.yml", "*.properties")

		trimTrailingWhitespace()
		indentWithSpaces()
		endWithNewline()
	}

	kotlin {
		ktlint("0.43.2").userData(mapOf("disabled_rules" to "no-wildcard-imports"))
		trimTrailingWhitespace()
		endWithNewline()
	}
}

