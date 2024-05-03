import org.springframework.boot.gradle.tasks.bundling.BootJar

tasks.getByName<BootJar>("bootJar") {
    enabled = true
}

tasks.getByName<Jar>("jar") {
    enabled = true
}

dependencies{
    implementation("org.springframework.boot:spring-boot-starter-data-cassandra-reactive")
}