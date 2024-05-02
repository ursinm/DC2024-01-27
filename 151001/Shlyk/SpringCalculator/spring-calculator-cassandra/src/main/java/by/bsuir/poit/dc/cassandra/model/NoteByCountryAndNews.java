package by.bsuir.poit.dc.cassandra.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

/**
 * @author Paval Shlyk
 * @since 04/04/2024
 */
@Deprecated
//@Table("note_by_country_and_news")
@AllArgsConstructor
@Getter
@Setter
public class NoteByCountryAndNews {
    @PrimaryKeyColumn(
	name = "country",
	type = PrimaryKeyType.PARTITIONED,
	ordinal = 0)
    private String country;
    @PrimaryKeyColumn(
	name = "news_id",
	type = PrimaryKeyType.CLUSTERED,
	ordering = Ordering.ASCENDING,
	ordinal = 1)
    private Long newsId;
    @PrimaryKeyColumn(
	name = "note_id",
	type = PrimaryKeyType.CLUSTERED,
	ordering = Ordering.ASCENDING,
	ordinal = 2)
    private Long id;
    @Column
    private String content;
}
