package by.bsuir.poit.dc.rest.api.dto.response;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link by.bsuir.poit.dc.rest.model.Label}
 */
@Data
@Builder
@ProtobufClass
@AllArgsConstructor
@NoArgsConstructor
public class LabelDto {
    private Long id;
    private String name;
}