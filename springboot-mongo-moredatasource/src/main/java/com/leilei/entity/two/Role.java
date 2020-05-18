package com.leilei.entity.two;

import lombok.Builder;
import lombok.Data;
import org.bson.BsonDateTime;
import org.bson.types.BSONTimestamp;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * @author : leilei
 * @date : 16:09 2020/2/16
 * @desc : 第二个数据源中的文档实体类
 */
@Data
@Document("role")
@Builder
public class Role {
    @Id
    private Long id;

    private String roleName;

    private Integer age;

    private LocalDateTime creatTime;
}
