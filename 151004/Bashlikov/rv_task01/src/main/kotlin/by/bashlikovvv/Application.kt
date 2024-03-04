package by.bashlikovvv

import by.bashlikovvv.data.configureDatabases
import by.bashlikovvv.api.controllers.configureRouting
import by.bashlikovvv.api.controllers.configureSerialization
import by.bashlikovvv.di.appModule
import by.bashlikovvv.di.dataModule
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin

fun main(args: Array<String>) = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    install(Koin) {
        modules(dataModule, appModule)
    }
    configureSerialization()
    configureDatabases()
    configureRouting()
}
