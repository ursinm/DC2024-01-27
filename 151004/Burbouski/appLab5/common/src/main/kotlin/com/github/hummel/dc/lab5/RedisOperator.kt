package com.github.hummel.dc.lab5

import io.lettuce.core.RedisClient
import io.lettuce.core.api.sync.RedisCommands

var id: Int = 0

private lateinit var operator: RedisCommands<String, String>

fun configureRedis() {
	val redisClient = RedisClient.create("redis://localhost")
	val connection = redisClient.connect()
	operator = connection.sync()

	testViaRedis("From Redis", "Connection established!")
}

fun testViaRedis(key: String, value: String) {
	operator.set(key, value)
	val test = operator.get(key)
	println("Key: $key, Value: $test")
}