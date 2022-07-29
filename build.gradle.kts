import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.7.0"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.6.20"
	kotlin("plugin.spring") version "1.6.20"
	kotlin("plugin.jpa") version "1.6.20"
	kotlin("kapt") version "1.6.20"
	id("com.diffplug.spotless") version "6.9.0"
	id ("org.flywaydb.flyway") version "7.7.0"
	id("jacoco")
	id("idea")
	id("java")
}

group = "io.rafaelribeiro"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

val jupiterVersion = "5.8.2"
val restAssuredVersion = "5.0.0"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-cache")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-graphql")
	implementation("org.springdoc:springdoc-openapi-ui:1.6.6")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("io.jsonwebtoken:jjwt:0.9.1")
	implementation("org.hibernate:hibernate-validator:7.0.4.Final")
	implementation("javax.validation:validation-api:2.0.1.Final")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	implementation("me.paulschwarz:spring-dotenv:2.5.4")

	// -- Tracing
	implementation("com.datadoghq:dd-trace-api:0.102.0")
	implementation("io.opentracing.contrib:opentracing-spring-web-starter:4.1.0")
	runtimeOnly("net.logstash.logback:logstash-logback-encoder:7.2")
	implementation("ch.qos.logback:logback-classic:1.2.11")
	implementation("ch.qos.logback:logback-core:1.2.11")

	runtimeOnly("org.flywaydb:flyway-core")
	runtimeOnly("org.postgresql:postgresql")
	runtimeOnly("com.h2database:h2")
	testImplementation("io.rest-assured:rest-assured:${restAssuredVersion}")
	testImplementation("io.rest-assured:json-path:${restAssuredVersion}")
	testImplementation("io.rest-assured:xml-path:${restAssuredVersion}")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.mockk:mockk:1.12.3")
	testImplementation("com.ninja-squad:springmockk:3.1.1")
	testImplementation("org.springframework.graphql:spring-graphql-test:1.0.0")
	testImplementation("org.junit.jupiter:junit-jupiter-api:${jupiterVersion}")
	testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${jupiterVersion}")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
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
		xml.isEnabled = true
		csv.isEnabled = false
		html.isEnabled = false
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

