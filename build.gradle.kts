import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	kotlin("jvm") version "1.5.31"
	id("nu.studer.jooq") version "5.2"
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

	implementation("org.springframework.boot:spring-boot-starter-jdbc:2.7.0")
	jooqGenerator("org.postgresql:postgresql:42.5.0")
	//implementation("org.flywaydb:flyway-core:9.3.1")
	implementation("org.springframework.boot:spring-boot-starter-jooq:2.7.0")
	implementation("org.postgresql:postgresql:42.5.0")
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

val jooqJdbcUrl = "jdbc:postgresql://localhost:5432/recipes"
val jooqSchema = "public"
val jooqUser = "ksenia"
val jooqPassword = "ksenia"

jooq {
	configurations {
		create("main") {
			generateSchemaSourceOnCompilation.set(false)
			jooqConfiguration.apply {
				logging = org.jooq.meta.jaxb.Logging.WARN
				jdbc.apply {
					driver = "org.postgresql.Driver"
					url = jooqJdbcUrl
					user = jooqUser
					password = jooqPassword
				}
				generator.apply {
					name = "org.jooq.codegen.DefaultGenerator"
					database.apply {
						name = "org.jooq.meta.postgres.PostgresDatabase"
						inputSchema = jooqSchema
						includes = "public.*"
					}
					generate.apply {
						isJavaTimeTypes = true
						isDeprecated = false
						isRecords = true
						isImmutablePojos = false
						isFluentSetters = true
					}
					target.apply {
						packageName = "com.qecodingcamp.applepie"
						directory = "build/generated-src/jooq/main" // default (can be omitted)
					}
					strategy.name = "org.jooq.codegen.DefaultGeneratorStrategy"
				}
			}
		}
	}
}
tasks.named("generateJooq").configure {
	//dependsOn(tasks.named("flywayMigrate"))
	inputs.files(fileTree("src/main/resources/db/migration"))
		.withPropertyName("migrations")
		.withPathSensitivity(PathSensitivity.RELATIVE)
	outputs.cacheIf { true }
}
