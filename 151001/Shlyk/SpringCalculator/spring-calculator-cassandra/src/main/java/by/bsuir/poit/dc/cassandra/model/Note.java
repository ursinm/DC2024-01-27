package by.bsuir.poit.dc.cassandra.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

/**
 * @author Paval Shlyk
 * @since 06/03/2024
 */
@Table("news_note")
@AllArgsConstructor
@Getter
@Setter
public class Note {
    @PrimaryKeyColumn(
	name = "note_id",
	type = PrimaryKeyType.PARTITIONED,
	ordinal = 0)
    private Long id;
    @Column
    private Long newsId;
//    @PrimaryKeyColumn(
//	name = "country",
//	type = PrimaryKeyType.CLUSTERED,
//	ordinal = 2)
    @Column
    private String country;
    @Column
    private String content;
}
