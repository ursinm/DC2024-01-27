package by.rusakovich.discussion.model;


import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.NamingStrategy;
import com.datastax.oss.driver.api.mapper.entity.naming.NameConverter;
import edu.umd.cs.findbugs.annotations.NonNull;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

@Getter
@Setter
@NamingStrategy(customConverterClass = DCNameConvention.class)
public class Note {
    @PrimaryKeyColumn(name = "country", type= PrimaryKeyType.PARTITIONED)
    private String country;
    @PrimaryKeyColumn(name = "news_id", ordinal = 0, type = PrimaryKeyType.CLUSTERED)
    private Long newsId;
    @PrimaryKeyColumn(name = "id", ordinal = 1, type= PrimaryKeyType.CLUSTERED)
    private Long id;
    private String content;
}

