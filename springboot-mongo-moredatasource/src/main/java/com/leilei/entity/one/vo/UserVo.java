package com.leilei.entity.one.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("user")
public class UserVo {
    @Id
    private Long id;

    private String userName;

    private String sex;

    private Integer age;

    private LocalDateTime creatTime;

    private Integer ageSum;

    private Integer total;

}
