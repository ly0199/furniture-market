package com.furniture.market.service;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Lijq
 * @date 2018/4/26 9:32
 * @description
 */
public class DateTest {

    @Test
    public void calenderTest() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        calendar.add(Calendar.DATE, 15);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        System.out.println(calendar.getTime());
    }


    @Test
    public void math() throws Exception {

        Date date1 = DateUtils.parseDate("2018-06-28 00:11:22", "yyyy-MM-dd HH:mm:ss");
        Date date2 = DateUtils.parseDate("2018-05-26 00:11:22", "yyyy-MM-dd HH:mm:ss");

        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);

        System.out.println(" 月 ->" + (calendar2.get(Calendar.MONTH) - calendar1.get(Calendar.MONTH)));
        System.out.println(" 年 ->" + (calendar2.get(Calendar.YEAR) - calendar1.get(Calendar.YEAR)));


    }
}
