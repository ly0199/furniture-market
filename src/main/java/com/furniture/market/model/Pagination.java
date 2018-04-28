package com.furniture.market.model;

import lombok.Data;

import java.util.LinkedHashMap;

/**
 * @author Lijq
 * @date 2018/4/23 13:40
 * @description
 */
@Data
public class Pagination {

    private int page = 0;
    private int size = 20;
    private LinkedHashMap<String, String> sorts = new LinkedHashMap<>();
    private LinkedHashMap<String, Object> querys = new LinkedHashMap<>();


}
