package com.leilei.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lei
 * @create 2022-08-26 14:46
 * @desc 分页对象
 **/
@Data
public class PageVO<T> {
    /**
     * 当前页
     */
    private Integer pageIndex;
    /**
     * 当前页最大长度
     */
    private Integer pageSize;
    /**
     * 总数
     */
    private Integer total;
    /**
     * 总页数
     */
    private Integer pages;
    /**
     * 当前页数据
     */
    private List<T> data;


    public static <T> PageVO<T> emptyResult() {
        PageVO<T> page = new PageVO<>();
        page.setPageIndex(1);
        page.setPageSize(10);
        page.setTotal(0);
        page.setPages(0);
        page.setData(new ArrayList<>());
        return page;
    }

    public static <T> PageVO<T> getPageResult(List<T> list, Integer pageIndex, Integer pageSize, Integer countSize) {
        PageVO<T> page = new PageVO<>();
        page.setPageIndex(pageIndex);
        page.setPageSize(pageSize);
        page.setTotal(countSize);
        page.setPages(countSize % pageSize == 0 ? countSize / pageSize : countSize / pageSize + 1);
        page.setData(list);
        return page;
    }
}
