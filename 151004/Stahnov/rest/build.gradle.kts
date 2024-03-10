plugins {
	java
	id("org.springframework.boot") version "3.2.3"
	id("io.spring.dependency-management") version "1.1.4"
}

dependencies{
	implementation(project(":SimpleRepository"))
	implementation(project(":Beans"))
}

allprojects{
	task("hello").doLast{
		println("Hello, I'm ${project.name}")
	}

	apply{plugin("java")}
	apply{plugin("org.springframework.boot")}
	apply{plugin("io.spring.dependency-management")}

	group = "com.distributed_computing"
	version = "0.0.1-SNAPSHOT"

	configurations {
		compileOnly {
			extendsFrom(configurations.annotationProcessor.get())
		}
	}

	tasks.withType<Test> {
		useJUnitPlatform()
	}

	java {
		sourceCompatibility = JavaVersion.VERSION_17
	}

	repositories {
		mavenCentral()
	}

	dependencies {
		implementation("org.springframework.boot:spring-boot-starter-validation")
		implementation("org.springframework.boot:spring-boot-starter-web")
		implementation("org.modelmapper:modelmapper:3.0.0")
		compileOnly("org.projectlombok:lombok")
		annotationProcessor("org.projectlombok:lombok")
		testImplementation("org.springframework.boot:spring-boot-starter-test")
	}

}






