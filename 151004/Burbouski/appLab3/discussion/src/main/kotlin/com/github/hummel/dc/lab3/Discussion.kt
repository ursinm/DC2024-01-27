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

//вводить (Ctrl + Shift + V) в Exec Docker'а на запущенном контейнере Cassandra с 9042:9042
//cqlsh
//CREATE KEYSPACE distcomp WITH REPLICATION = {'class' : 'NetworkTopologyStrategy', 'datacenter1' : 3};
//CREATE TABLE distcomp.tbl_message_by_country (country text, issue_id bigint, id bigint, content text, PRIMARY KEY ((country), issue_id, id));
fun main() {
	embeddedServer(Netty, port = 24130, module = Application::discussion).start(wait = true)
}

fun Application.discussion() {
	install(DoubleReceive)
	install(Koin) {
		modules(dataModule, appModule)
	}
	configureSerialization()
	configureRouting()
}