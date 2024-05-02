package by.bsuir.poit.dc.rest.api.dto.response;

import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * DTO for {@link by.bsuir.poit.dc.rest.model.News}
 */
@Data
@Builder
@ProtobufClass
@AllArgsConstructor
@NoArgsConstructor
public class NewsDto {
    private long id;
    private String title;
    private String content;
    private Date created;
    private Date modified;
    private long userId;
}