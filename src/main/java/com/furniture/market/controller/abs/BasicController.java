package com.furniture.market.controller.abs;

import com.furniture.market.model.Pagination;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 基础控制器
 *
 * @author Lijq
 */
public class BasicController {


    /**
     * 获取分页数据
     *
     * @param request request
     * @return {@link Pagination}
     */
    public Pagination getPagination(HttpServletRequest request) {
        Pagination pagination = new Pagination();
        String pageIndex = request.getParameter("pageIndex");
        String pageSize = request.getParameter("pageSize");
        if (StringUtils.isNotBlank(pageIndex)) {
            pagination.setPage(NumberUtils.toInt(pageIndex));
        }
        if (StringUtils.isNotBlank(pageSize)) {
            pagination.setSize(NumberUtils.toInt(pageSize));
        }
        return pagination;
    }


}
