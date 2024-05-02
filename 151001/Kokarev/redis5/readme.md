docker postgres start:
    docker run -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=distcomp -p 5432:5432 -d postgres
    docker exec -it cont bash
    psql -U postgres -d distcomp
    CREATE SCHEMA IF NOT EXISTS distcomp;

docker cassandra start:
    docker run -p 9042:9042 -e CASSANDRA_KEYSPACE=distcomp -e CASSANDRA_DC=datacenter1 -e CASSANDRA_CLUSTER_NAME=myCluster -d cassandra
    docker exec -it cont bash
    cqlsh
    CREATE KEYSPACE distcomp WITH replication = {'class':'SimpleStrategy','replication_factor':'1'};

kafta start:
    docker network create kafkanet
    docker run -d --network=kafkanet --name=zookeeper -e ZOOKEEPER_CLIENT_PORT=2181 -e ZOOKEEPER_TICK_TIME=2000 -p 2181:2181 confluentinc/cp-zookeeper
    docker run -d --network=kafkanet --name=kafka -e KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181 -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092 -e KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR=1 -p 9092:9092 confluentinc/cp-kafka

redis start:
    docker run -p 6379:6379 -d redis
