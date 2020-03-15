package com.leilei.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @author : leilei
 * @date : 11:26 2020/3/15
 * @desc :
 */
@Data
@Document(collation = "studentClass")
@NoArgsConstructor
@AllArgsConstructor
public class StudentClass implements Serializable {
    @Id
    private Long id;
    private String className;
    /**关联学校*/
    private Long schoolId;
}
