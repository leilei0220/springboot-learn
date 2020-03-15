package com.leilei.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @author : leilei
 * @date : 16:54 2020/3/15
 * @desc :
 */
@Data
@Document(collation = "school")
@NoArgsConstructor
@AllArgsConstructor
public class School implements Serializable {
    @Id
    private Long id;
    private String schoolName;
    /**关联城市ID*/
    private Long cityId;
}
