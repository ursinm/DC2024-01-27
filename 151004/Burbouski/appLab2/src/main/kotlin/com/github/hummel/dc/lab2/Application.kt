package com.github.hummel.dc.lab2

import com.github.hummel.dc.lab2.controller.configureRouting
import com.github.hummel.dc.lab2.controller.configureSerialization
import com.github.hummel.dc.lab2.module.appModule
import com.github.hummel.dc.lab2.module.dataModule
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.koin.ktor.plugin.Koin
import java.sql.Connection
import java.sql.DriverManager

fun main() {
	embeddedServer(Netty, port = 24110, module = Application::module).start(wait = true)
}

fun Application.module() {
	install(Koin) {
		dataModule.single<Connection> { connectToPostgres(embedded = true) }
		modules(dataModule, appModule)
	}
	configureSerialization()
	configureRouting()}

fun Application.connectToPostgres(embedded: Boolean): Connection {
	Class.forName("org.postgresql.Driver")

	if (embedded) {
		return DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "root", "")
	}

	val url = environment.config.property("postgres.url").getString()
	val user = environment.config.property("postgres.user").getString()
	val password = environment.config.property("postgres.password").getString()

	return DriverManager.getConnection(url, user, password)
}