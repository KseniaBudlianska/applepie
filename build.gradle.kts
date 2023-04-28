import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	application
	kotlin("jvm") version "1.5.31"
	id("nu.studer.jooq") version "5.2"
	id("info.solidsoft.pitest") version "1.9.0"
	id("org.flywaydb.flyway") version "9.10.1"
	id("org.springframework.boot") version "2.7.0"
	kotlin("plugin.spring") version "1.5.31"
}

group = "com.qecodingcamp"
version = "0.0.1-SNAPSHOT"

repositories {
	mavenCentral()
}

application {
	mainClass.set("com.qecodingcamp.applepie.ApplePieSpringAppKt")
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
	testImplementation("io.rest-assured:rest-assured:3.0.0")

	implementation("org.springframework.boot:spring-boot-starter-jdbc:2.7.0")
	jooqGenerator("org.postgresql:postgresql:42.5.0")
	implementation("org.flywaydb:flyway-core:9.10.1")
	implementation("org.springframework.boot:spring-boot-starter-jooq:2.7.0")
	implementation("org.postgresql:postgresql:42.5.0")
	testImplementation("org.testcontainers:postgresql:1.17.5")
	testImplementation("org.testcontainers:junit-jupiter:1.17.5")
	implementation("org.springdoc:springdoc-openapi-ui:1.6.14")
}

tasks.test {
	useJUnitPlatform()
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
	dependsOn(tasks.named("flywayMigrate"))
	inputs.files(fileTree("src/main/resources/db/migration"))
		.withPropertyName("migrations")
		.withPathSensitivity(PathSensitivity.RELATIVE)
	outputs.cacheIf { true }
}

flyway {
	url = jooqJdbcUrl
	user = jooqUser
	password = jooqPassword
}

pitest {
	//adds dependency to org.pitest:pitest-junit5-plugin and sets "testPlugin" to "junit5"
	junit5PluginVersion.set("1.0.0")    //or 0.15 for PIT <1.9.0
	// ...
}

/*tasks.withType<Jar> {
	manifest {
		attributes["Main-Class"] = "com.qecodingcamp.applepie.ApplePieSpringAppKT"
	}

	val dependencies = configurations
		.runtimeClasspath
		.get()
		.map(::zipTree)
	from(dependencies)
	duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}*/

tasks {
	val fatJar = register<Jar>("fatJar") {
		dependsOn.addAll(listOf("compileJava", "compileKotlin", "processResources")) // We need this for Gradle optimization to work
		archiveClassifier.set("standalone") // Naming the jar
		duplicatesStrategy = DuplicatesStrategy.EXCLUDE
		manifest { attributes(mapOf("Main-Class" to application.mainClass)) } // Provided we set it up in the application plugin configuration
		val sourcesMain = sourceSets.main.get()
		val contents = configurations.runtimeClasspath.get()
			.map { if (it.isDirectory) it else zipTree(it) } +
				sourcesMain.output
		from(contents)
	}
	build {
		dependsOn(fatJar) // Trigger fat jar creation during build
	}
}
