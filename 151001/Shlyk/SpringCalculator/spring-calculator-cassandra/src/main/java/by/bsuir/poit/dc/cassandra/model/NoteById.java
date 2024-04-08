package by.bsuir.poit.dc.cassandra.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

/**
 * @author Paval Shlyk
 * @since 06/03/2024
 */
@Table("note_by_id")
@AllArgsConstructor
@Getter
@Setter
public class NoteById {
    @PrimaryKeyColumn(
	name = "note_id",
	type = PrimaryKeyType.PARTITIONED,
	ordinal = 0)
    private long id;
    @Column("news_id")
    private long newsId;
    @Column("content")
    @NotNull
    private String content;
    @Column("status")
    private Short status;
//    @Column("status")
//    private long status;
}
