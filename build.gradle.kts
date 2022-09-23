import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	kotlin("jvm") version "1.5.31"
	application
}

group = "com.qecodingcamp"
version = "0.0.1-SNAPSHOT"

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.apache.commons:commons-csv:1.9.0")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter:2.7.0")
	implementation("org.springframework.boot:spring-boot-starter-web:2.7.0")
	testImplementation("org.springframework.boot:spring-boot-starter-test:2.7.0")
	testImplementation("org.junit.jupiter:junit-jupiter")
	implementation(kotlin("stdlib-jdk8"))
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.11.2")
	testImplementation("io.mockk:mockk:1.10.0")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.rest-assured:rest-assured:5.2.0")
}

tasks.test {
	useJUnitPlatform()
}

application {
	mainClassName = "ApplePieSpringAppKt"
}

tasks.compileKotlin {
	kotlinOptions{
		jvmTarget="11"
	}
}

tasks.compileTestKotlin {
	kotlinOptions{
		jvmTarget="11"
	}
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
	jvmTarget = "1.8"
}
