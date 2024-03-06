package by.bsuir.poit.dc.cassandra.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

/**
 * @author Paval Shlyk
 * @since 06/03/2024
 */
@Table
@AllArgsConstructor
@Getter
@Setter
public class Note {
    @PrimaryKey
    private Long id;
    private String country;
    private Long newsId;
    private String content;
}
