package com.leilei.entity.one;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**user
 * @author : leilei
 * @date : 16:08 2020/2/16
 * @desc : 第一个数据源中的文档实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("user")
public class User {
    @Id
    private Long id;

    private String userName;
    /**默认性别为男*/
    private String sex="男";

    private Integer age;

    private LocalDateTime creatTime;
}
