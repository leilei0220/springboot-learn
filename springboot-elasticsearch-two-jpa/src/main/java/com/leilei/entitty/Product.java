package com.leilei.entitty;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
 * @author lei
 * @version 1.0
 * @date 2020/7/12 14:50
 */
@Data
@Document(indexName = "springboot-es",shards = 5,replicas = 1)
public class Product implements Serializable {
    @Id
    private Long id; //主键
    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String name;//产品名 ik详细分词
    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String brand;//产品品牌  ik详细分词
    @Field(type = FieldType.Double)
    private Double price; //价格
    @Field(type = FieldType.Long)
    private Long createTime;//创建时间
    @Field(type = FieldType.Boolean)
    private Boolean up; //是否上架
}
