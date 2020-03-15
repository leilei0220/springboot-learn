package com.leilei.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @author : leilei
 * @date : 17:22 2020/3/15
 * @desc :
 */
@Data
@Document(collation = "city")
@NoArgsConstructor
@AllArgsConstructor
public class City implements Serializable {
    @Id
    private Long id;
    private String cityName;

}
