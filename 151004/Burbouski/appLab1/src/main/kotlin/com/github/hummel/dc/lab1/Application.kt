package com.github.hummel.dc.lab1

import com.github.hummel.dc.lab1.controller.configureRouting
import com.github.hummel.dc.lab1.controller.configureSerialization
import com.github.hummel.dc.lab1.module.appModule
import com.github.hummel.dc.lab1.module.dataModule
import io.ktor.server.application.*
import io.ktor.server.netty.*
import org.koin.ktor.plugin.Koin

fun main(args: Array<String>): Unit = EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
	install(Koin) {
		modules(dataModule, appModule)
	}
	configureSerialization()
	configureRouting()
}