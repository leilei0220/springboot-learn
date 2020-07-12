package com.leilei.controller;

import com.leilei.entitty.Product;
import com.leilei.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author lei
 * @version 1.0
 * @date 2020/7/12 15:02
 */
@RestController
@RequestMapping("/es")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping
    public String createIndex() {
        boolean orUpdateIndex = productService.createOrUpdateIndex();
        return "创建索引" + orUpdateIndex;
    }

    @GetMapping
    public String indexExists() {
        boolean b = productService.indexExists();
        return "索引是否存在?" + b;
    }

    @DeleteMapping
    public String delete() {
        boolean b = productService.deleteIndex();
        return "索引是否删除成功" + b;
    }

    @PostMapping("/product")
    public Product save(Product product) {
        product.setCreateTime(System.currentTimeMillis());
        return productService.save(product);
    }

    @DeleteMapping("/product/{id}")
    public String delete(@PathVariable("id") Long id) {
        Boolean delete = productService.delete(id);
        return "删除数据" + delete;
    }


    @GetMapping("/product/{id}")
    public Optional<Product> getOne(@PathVariable("id") Long id) {
        Optional<Product> one = productService.getProduct(id);
        return one;
    }
    @GetMapping("/product/findAll/{page}/{size}/{field}/{order}")
    public Page<Product> delete( @PathVariable("page") Integer page,
                          @PathVariable("size") Integer size,
                          @PathVariable("field") String field,
                          @PathVariable("order") String order) {
        Page<Product> products = productService.findAll(page,size,field,order);
        return products;
    }
    @GetMapping("/product/findByIdBetween/{id1}/{id2}/{page}/{size}")
    public Page<Product> findByIdBetween( @PathVariable("id1") Long id1,
                          @PathVariable("id2") Long id2,
                          @PathVariable("page") Integer page,
                          @PathVariable("size") Integer size) {
        Page<Product> products = productService.findByIdBetween(id1,id2,page,size);
        return products;
    }


    @GetMapping("/product/byBrand/{brand}")
    public List<Product> byBrand(@PathVariable("brand") String brand) {
        List<Product> products = productService.findAllByBrand(brand);
        return products;
    }

    @GetMapping("/product/byBrand/{brand}/{page}/{size}")
    public Page<Product> byBrandPage(@PathVariable("brand") String brand, @PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        Page<Product> allByBrandPage = productService.findAllByBrandPage(brand, page, size);
        return allByBrandPage;
    }


}
