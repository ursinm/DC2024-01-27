package by.bsuir.poit.dc.rest.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Paval Shlyk
 * @since 31/01/2024
 */
@Entity
@Table(name = "news_label")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewsLabel {
    @EmbeddedId
    private NewsLabelId id;

    @ManyToOne
    @MapsId("newsId")
    @JoinColumn(name = "news_id", referencedColumnName = "id")
    private News news;

    @ManyToOne
    @MapsId("labelId")
    @JoinColumn(name = "label_id", referencedColumnName = "id")
    private Label label;
}
