package com.furniture.market.controller;

import com.alibaba.fastjson.JSONObject;
import com.furniture.market.controller.abs.BasicController;
import com.furniture.market.entity.Compact;
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
 * @date 2018/4/16 18:01
 * @description
 */
@Controller
@RequestMapping(value = "/compact")
public class CompactController extends BasicController {

    @Autowired
    private ICompactService compactService;
    @Autowired
    private IRentRecordService rentRecordService;

    @GetMapping("/page")
    public String page(ModelMap model) {
        return "compact/page";
    }

    @PostMapping(value = "/page")
    @ResponseBody
    public MiniPage page(ModelMap model, HttpServletRequest request) {
        Pagination pagination = getPagination(request);
        String keywords = request.getParameter("key");
        if (StringUtils.isBlank(keywords)) {
            return compactService.page(pagination);
        } else {
            return compactService.search(pagination, keywords);
        }
    }

    @GetMapping(value = "/add")
    public String add(ModelMap model) {
        return "compact/add";
    }

    @GetMapping(value = "/edit")
    public String edit(ModelMap model) {
        return "compact/edit";
    }


    @ResponseBody
    @GetMapping(value = "/info")
    public Compact compact(ModelMap model, HttpServletRequest request) {
        return compactService.getById(Integer.valueOf(request.getParameter("id")));
    }

    @GetMapping(value = "/no")
    public ResponseEntity no(ModelMap model, HttpServletRequest request) {
        HashMap<String, Boolean> map = new HashMap<>();
        map.put("exists", false);

        String no = request.getParameter("no");
        if (StringUtils.isNotBlank(no)) {
            Compact compact = compactService.getByNo(no);
            if (compact != null) {
                map.put("exists", true);
                return ResponseEntity.status(HttpStatus.OK).body(map);
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(map);
    }


    @PostMapping(value = "save")
    public ResponseEntity save(ModelMap model, HttpServletRequest request) {

        JSONObject vo = JSONObject.parseObject(request.getParameter("vo"));

        vo.keySet().forEach(System.out::println);

        String no = vo.getString("no");
        String merchant = vo.getString("merchant");
        String name = vo.getString("name");

        Date startDate = vo.getDate("startDate");
        Date endDate = vo.getDate("endDate");

        String booth = vo.getString("booth");
        BigDecimal area = vo.getBigDecimal("area");
        BigDecimal rentOfArea = vo.getBigDecimal("rentOfArea");

        int countOfMonth = vo.getIntValue("countOfMonth");
        int getway = vo.getIntValue("getway");

        BigDecimal discount = vo.getBigDecimal("discount");

        BigDecimal rentOfCompact = vo.getBigDecimal("rentOfCompact");
        BigDecimal rentOfReceivable = vo.getBigDecimal("rentOfReceivable");
        BigDecimal rentOfAreaMonth = vo.getBigDecimal("rentOfAreaMonth");

        BigDecimal rentOfMonthTotal = vo.getBigDecimal("rentOfMonthTotal");

        BigDecimal rentOfManageMonth = vo.getBigDecimal("rentOfManageMonth");
        BigDecimal rentOfCharges = vo.getBigDecimal("rentOfCharges");
        BigDecimal rentOfManage = vo.getBigDecimal("rentOfManage");
        BigDecimal rentOfChargesMonth = vo.getBigDecimal("rentOfChargesMonth");

        BigDecimal rentOfDiscounts = vo.getBigDecimal("rentOfDiscounts");


        Compact compact = new Compact();

        // 合同基础信息
        compact.setNo(no);
        compact.setMerchant(merchant);
        compact.setName(name);
        compact.setStartDate(startDate);
        compact.setEndDate(endDate);
        compact.setCountOfMonth(countOfMonth);

        // 摊位信息 及收租类型、折扣
        compact.setBooth(booth);
        compact.setArea(area);
        compact.setGetway(getway);
        compact.setDiscount(discount);

        // 租金相关
        compact.setRentOfArea(rentOfArea);
        compact.setRentOfAreaMonth(rentOfAreaMonth);

        compact.setRentOfManage(rentOfManage);
        compact.setRentOfManageMonth(rentOfManageMonth);

        compact.setRentOfCharges(rentOfCharges);
        compact.setRentOfChargesMonth(rentOfChargesMonth);

        compact.setRentOfMonthTotal(rentOfMonthTotal);
        compact.setRentOfCompact(rentOfCompact);

        compact.setRentOfDiscounts(rentOfDiscounts);
        compact.setRentOfReceivable(rentOfReceivable);


        // 押金
        String remark = vo.getString("remark");
        BigDecimal rentOfPledge = vo.getBigDecimal("rentOfPledge");

        compact.setRentOfPledge(rentOfPledge);
        compact.setRemark(remark);


        // 初次创建时已收到为0
        compact.setRentOfReceived(BigDecimal.ZERO);

        compact = compactService.save(compact);

        Map<String, Object> map = new HashMap<>();
        if (compact.getId() != null) {
            map.put("successed", true);
            return ResponseEntity.ok(map);
        } else {

            map.put("successed", false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
        }

    }

    @ResponseBody
    @PostMapping(value = "/returnPledge")
    public ResponseEntity returnPledge(ModelMap model, HttpServletRequest request) {

        Map<String, Object> map = new HashMap<>();

        String compactId = request.getParameter("id");
        if (StringUtils.isNotBlank(compactId)) {
            compactService.updateReturnPledge(Integer.valueOf(compactId));
            map.put("successed", true);
            return ResponseEntity.ok(map);
        } else {
            map.put("successed", false);
            return ResponseEntity.ok(map);
        }
    }


    /**
     * 即将到期
     *
     * @param modelMap
     * @return
     */
    @GetMapping(value = "/expire/page")
    public String expirePage(ModelMap modelMap) {
        return "compact/expire-page";
    }


    @PostMapping(value = "/expire/page")
    @ResponseBody
    public MiniPage expireMiniPage(ModelMap model, HttpServletRequest request) {
        Pagination pagination = getPagination(request);
        return compactService.expirePage(pagination);
    }


}
