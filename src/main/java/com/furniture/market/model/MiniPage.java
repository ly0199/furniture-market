package com.furniture.market.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 适用于 Mini UI 的分页数据
 *
 * @author Lijq
 */
@Data
public class MiniPage implements Serializable {

    private long total;
    private List<?> data;
}
