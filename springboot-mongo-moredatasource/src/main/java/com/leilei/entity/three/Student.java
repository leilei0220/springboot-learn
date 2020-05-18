package com.leilei.entity.three;

import lombok.Data;
import org.bson.BsonTimestamp;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * @author : leilei
 * @date : 16:10 2020/2/16
 * @desc :第三个数据源中的文档实体类
 */
@Data
@Document("student")
public class Student {
    @Id
    private Long id;

    private String studentName;

    private Integer age;

    private LocalDateTime creatTime;
}
