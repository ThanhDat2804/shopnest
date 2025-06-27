package com.mall.shopnest.api;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
public class CommonPage<T> {
    /**
     * Current page number (0-based from Spring)
     */
    private Integer pageNum;

    /**
     * Page size
     */
    private Integer pageSize;

    /**
     * Total number of pages
     */
    private Integer totalPage;

    /**
     * Total number of items
     */
    private Long total;

    /**
     * Data for the current page
     */
    private List<T> list;

    /**
     * Convert Spring Data's Page<T> to CommonPage<T>
     */
    public static <T> CommonPage<T> restPage(Page<T> pageInfo) {
        CommonPage<T> result = new CommonPage<>();
        result.setTotalPage(pageInfo.getTotalPages());
        result.setPageNum(pageInfo.getNumber() + 1); // Convert 0-based to 1-based
        result.setPageSize(pageInfo.getSize());
        result.setTotal(pageInfo.getTotalElements());
        result.setList(pageInfo.getContent());
        return result;
    }
}