package com.github.hummel.dc.lab4

import com.datastax.driver.core.Cluster
import com.github.hummel.dc.lab4.controller.configureRouting
import com.github.hummel.dc.lab4.module.appModule
import com.github.hummel.dc.lab4.module.dataModule
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.doublereceive.*
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import org.koin.ktor.plugin.Koin
import java.time.Duration
import java.util.*
import kotlin.concurrent.thread

//вводить (Ctrl + Shift + V) в Exec Docker'а на запущенном контейнере Cassandra с 9042:9042
//cqlsh
//CREATE KEYSPACE distcomp WITH REPLICATION = {'class' : 'NetworkTopologyStrategy', 'datacenter1' : 3};
//CREATE TABLE distcomp.tbl_message_by_country (country text, issue_id bigint, id bigint, content text, PRIMARY KEY ((country), issue_id, id));

lateinit var consumer: KafkaConsumer<String, String>

fun main() {
	embeddedServer(Netty, port = 24130, module = Application::discussion).start(wait = true)
}

fun Application.discussion() {
	install(DoubleReceive)
	install(Koin) {
		dataModule.single<Cluster> {
			Cluster.builder().withoutMetrics().addContactPoints("127.0.0.1").build()
		}
		modules(dataModule, appModule)
	}
	install(ContentNegotiation) {
		json()
	}
	configureRouting()
	configureKafka()
}

fun configureKafka() {

	val bootstrapServers = "localhost:9092"
	val topic = "app"
	val groupId = "app-id"

	val consumerProps = Properties()
	consumerProps[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapServers
	consumerProps[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java.name
	consumerProps[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java.name
	consumerProps[ConsumerConfig.GROUP_ID_CONFIG] = groupId

	consumer = KafkaConsumer<String, String>(consumerProps)
	consumer.subscribe(listOf(topic))

	thread {
		while (true) {
			val records = consumer.poll(Duration.ofMillis(100))
			records.forEach { println(it.value()) }
		}
	}
}