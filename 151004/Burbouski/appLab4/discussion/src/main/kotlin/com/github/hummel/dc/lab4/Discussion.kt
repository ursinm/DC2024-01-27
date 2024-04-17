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

//вводить в терминал
//docker network create kafkanet
//docker run -d --network=kafkanet --name=zookeeper -e ZOOKEEPER_CLIENT_PORT=2181 -e ZOOKEEPER_TICK_TIME=2000 -p 2181:2181 confluentinc/cp-zookeeper
//docker run -d --network=kafkanet --name=kafka -e KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181 -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092 -e KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR=1 -p 9092:9092 confluentinc/cp-kafka

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