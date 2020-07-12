package com.leilei.service;

import com.leilei.dao.ProductRepository;
import com.leilei.entitty.Product;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author lei
 * @version 1.0
 * @date 2020/7/12 15:10
 */
@Service
public class ProductService {
    @Autowired
    private ElasticsearchRestTemplate esTemplate;
    @Autowired
    private ProductRepository productRepository;

    /**
     * 创建/更新索引
     */
    public boolean createOrUpdateIndex() {
        IndexOperations indexOperations = esTemplate.indexOps(Product.class);
        indexOperations.createMapping(Product.class);
        return indexOperations.create();

    }

    /**
     * 判断索引是否存在
     *
     * @return
     */
    public boolean indexExists() {
        IndexOperations indexOperations = esTemplate.indexOps(Product.class);
        return indexOperations.exists();
    }

    /**
     * 删除索引
     */
    public boolean deleteIndex() {
        IndexOperations indexOperations = esTemplate.indexOps(Product.class);
        return indexOperations.delete();
    }

    /**
     * 新增或者修改数据
     */
    public Product save(Product product) {
        return productRepository.save(product);
    }

    /**
     * 删除数据
     */
    public Boolean delete(Long id) {
        try {
            productRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据ID 查询一个
     *
     * @param id
     * @return
     */
    public Optional<Product> getProduct(Long id) {
        return productRepository.findById(id);
    }

    /**
     * 查询所有 可自定义正序/倒叙排列 默认倒叙
     *
     * @param page
     * @param size
     * @param field
     * @param order
     * @return
     */
    public Page<Product> findAll(Integer page, Integer size, String field, String order) {
        Page<Product> all = productRepository.findAll(
                PageRequest.of(page - 1, size,
                        Sort.by(!"desc".equals(order) ? Sort.Order.asc(field) : Sort.Order.desc(field))));
        return all;
    }

    /**
     * 区间查询，可分页
     *
     * @param id1
     * @param id2
     * @param page
     * @param size
     * @return
     */
    public Page<Product> findByIdBetween(Long id1, Long id2, Integer page, Integer size) {
        PageRequest of = PageRequest.of(page - 1, size);
        Page<Product> all = productRepository.findProductsByIdBetween(id1, id2, of);
        return all;
    }


    /**
     * 根据类型查询
     */
    public List<Product> findAllByBrand(String Brand) {
        List<Product> products = productRepository.findProductsByBrand(Brand);
        return products;
    }

    /**
     * 根据类型查询 且分页
     */
    public Page<Product> findAllByBrandPage(String brand, Integer page, Integer size) {

        NativeSearchQuery build = new NativeSearchQueryBuilder()
                //必须匹配
                .withQuery(QueryBuilders.matchQuery("brand", brand))
                //分页
                .withPageable(PageRequest.of(page - 1, size))
                //我根据ID倒叙排列
                .withSort(SortBuilders.fieldSort("id").order(SortOrder.DESC))
                .build();
        return productRepository.search(build);
    }
}
