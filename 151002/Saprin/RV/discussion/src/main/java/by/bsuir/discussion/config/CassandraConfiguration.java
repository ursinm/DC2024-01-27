package by.bsuir.discussion.config;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.type.codec.TypeCodec;
import com.datastax.oss.driver.api.core.type.codec.TypeCodecs;
import com.datastax.oss.driver.api.core.type.codec.registry.CodecRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;

import java.math.BigInteger;
import java.net.InetSocketAddress;

@Configuration
public class CassandraConfiguration {

    public @Bean CqlSession session() {
        return CqlSession.builder()
                .withKeyspace("keyspace_name")
                .addContactPoint(InetSocketAddress.createUnresolved("localhost", 9042))
                .withLocalDatacenter("datacenter1")
                .withKeyspace("keyspace_name")
                .build();
    }
}