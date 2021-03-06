import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.4.4"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.4.30"
	kotlin("plugin.spring") version "1.4.30"
	kotlin("plugin.jpa") version "1.4.32"
	id("org.jetbrains.kotlin.kapt") version "1.4.31"
	id("com.diffplug.spotless") version "5.11.0"
	id ("org.flywaydb.flyway") version "7.7.0"
	id("jacoco")
	id("idea")
	id("java")
}

group = "br.com.alura"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

val jupiterVersion = "5.7.1"
val restAssuredVersion = "4.0.0"
val swaggerVersion = "3.0.0"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-cache")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("io.jsonwebtoken:jjwt:0.9.1")
	implementation("io.springfox:springfox-swagger2:${swaggerVersion}")
	implementation("io.springfox:springfox-swagger-ui:${swaggerVersion}")
	implementation("org.hibernate:hibernate-validator:7.0.1.Final")
	implementation("javax.validation:validation-api:2.0.1.Final")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("org.flywaydb:flyway-core")
	runtimeOnly("org.postgresql:postgresql")
	runtimeOnly("com.h2database:h2")
	testImplementation("io.rest-assured:rest-assured:${restAssuredVersion}")
	testImplementation("io.rest-assured:json-path:${restAssuredVersion}")
	testImplementation("io.rest-assured:xml-path:${restAssuredVersion}")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.mockk:mockk:1.9.3")
	testImplementation("com.ninja-squad:springmockk:3.0.1")
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
	toolVersion = "0.8.5"
}

tasks.jacocoTestReport {
	group = "Reporting"
	reports {
		xml.isEnabled = true
		csv.isEnabled = false
		html.isEnabled = false
	}
}

task<Copy>("jacocoFixForCodeClimate") {
	from("build/reports/jacoco/test/jacocoTestReport.xml")
	into("build/reports/jacoco/test/")
	rename { fileName ->
		fileName.replace("jacocoTestReport.xml", "jacoco.xml")
	}
	filter { line -> line.replace("br/com/", "src/main/kotlin/br/com/") }
}

spotless {
	format("misc") {
		target(".gitignore", "*.md", "*.yml", "*.properties")

		trimTrailingWhitespace()
		indentWithSpaces()
		endWithNewline()
	}

	kotlin {
		ktlint()
		trimTrailingWhitespace()
		endWithNewline()
	}
}

