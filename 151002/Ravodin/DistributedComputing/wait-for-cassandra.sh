#!/bin/bash
set -e

# Ожидание доступности Cassandra
dockerize -wait tcp://cassandra-db:9042 -timeout 2m

# Запуск приложения
exec dotnet Forum.PostApi.dll
