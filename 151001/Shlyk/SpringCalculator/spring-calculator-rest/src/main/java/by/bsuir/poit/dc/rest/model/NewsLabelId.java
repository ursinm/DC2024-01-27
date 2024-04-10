package by.bsuir.poit.dc.rest.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Paval Shlyk
 * @since 31/01/2024
 */
@Embeddable
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewsLabelId implements Serializable {
    @Column(name = "news_id", nullable = false)
    private Long newsId;
    @Column(name = "label_id", nullable = false)
    private Long labelId;
}
