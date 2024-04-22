
import api.controller.configureRouting
import api.controller.configureSerialization
import di.appModule
import di.dataModule
import io.ktor.server.application.*
import io.ktor.server.plugins.doublereceive.*
import org.koin.ktor.plugin.Koin

fun Application.configureDiscussionModule() {
    install(DoubleReceive)
    configureKoin()
    configureSerialization()
    configureRouting()
}

internal fun Application.configureKoin() {
    install(Koin) {
        modules(dataModule, appModule)
    }
}