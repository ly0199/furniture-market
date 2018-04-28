package com.furniture.market.controller;

import com.alibaba.fastjson.JSONObject;
import com.furniture.market.controller.abs.BasicController;
import com.furniture.market.entity.RentRecord;
import com.furniture.market.model.MiniPage;
import com.furniture.market.model.Pagination;
import com.furniture.market.service.ICompactService;
import com.furniture.market.service.IRentRecordService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Lijq
 * @date 2018/4/25 13:22
 * @description
 */
@Controller
@RequestMapping(value = "/rent")
public class RentController extends BasicController {

    @Autowired
    private IRentRecordService rentRecordService;
    @Autowired
    private ICompactService compactService;

    @PostMapping(value = "/page")
    @ResponseBody
    public MiniPage rentPage(ModelMap model, HttpServletRequest request) {
        Pagination pagination = getPagination(request);
        String compactId = request.getParameter("compactId");
        if (StringUtils.isBlank(compactId)) {
            return new MiniPage();
        } else {
            return rentRecordService.page(pagination, compactId);
        }
    }

    @GetMapping(value = "/add")
    public String add(ModelMap model, HttpServletRequest request) {
        return "compact/add-rent";
    }

    @PostMapping(value = "save")
    public ResponseEntity save(ModelMap model, HttpServletRequest request) {

        JSONObject vo = JSONObject.parseObject(request.getParameter("vo"));

        Integer compactId = vo.getIntValue("compactId");
        Date startDate = vo.getDate("startDate");
        Date endDate = vo.getDate("endDate");
        BigDecimal rent = vo.getBigDecimal("rent");


        Map<String, Object> map = new HashMap<>();

        RentRecord rentRecord = new RentRecord();
        rentRecord.setStartDate(startDate);
        rentRecord.setEndDate(endDate);
        rentRecord.setRent(rent);

        rentRecord = rentRecordService.save(rentRecord, compactId);

        if (rentRecord.getId() != null) {
            map.put("successed", true);
            return ResponseEntity.ok(map);
        } else {
            map.put("successed", false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
        }
    }
}
