package com.github.hummel.dc.lab3

import com.github.hummel.dc.lab3.controller.configureRouting
import com.github.hummel.dc.lab3.controller.configureSerialization
import com.github.hummel.dc.lab3.module.appModule
import com.github.hummel.dc.lab3.module.dataModule
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.doublereceive.*
import org.koin.ktor.plugin.Koin

fun main() {
	embeddedServer(Netty, port = 24130, host = "0.0.0.0", module = Application::discussion).start(wait = true)
}

fun Application.discussion() {
	install(DoubleReceive)
	install(Koin) {
		modules(dataModule, appModule)
	}
	configureSerialization()
	configureRouting()
}