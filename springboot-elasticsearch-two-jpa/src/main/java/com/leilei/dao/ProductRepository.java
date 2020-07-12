package com.leilei.dao;

import com.leilei.entitty.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @author lei
 * @version 1.0
 * @date 2020/7/12 14:59
 * 继承ElasticsearchRepository即可实现基础的crud功能
 */
public interface ProductRepository extends ElasticsearchRepository<Product, Long> {

    Page<Product> findProductsByIdBetween(Long id1, Long id2, Pageable pageable);


    List<Product> findProductsByBrand(String brand);
}
