package by.bsuir.poit.dc.rest.api.dto.response;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link by.bsuir.poit.dc.rest.model.User}
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ProtobufClass
public class UserDto {
    private Long id;
    private String login;
    private String firstname;
    private String lastname;
}