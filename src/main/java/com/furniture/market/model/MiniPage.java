package com.furniture.market.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Lijq
 * @date 2018/4/18 9:40
 * @description
 */
@Data
public class MiniPage implements Serializable {

    private long total;
    private List<?> data;
}
